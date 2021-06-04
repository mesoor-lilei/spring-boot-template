package com.example.template.handler

import com.example.template.exception.OutException
import com.example.template.exception.PARAMETER_ERROR
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus.*
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException

/** 异常处理器 */
@RestControllerAdvice
class ExceptionHandler(
    private val response: HttpServletResponse
) {
    class Out(val message: String)

    @ExceptionHandler(OutException::class)
    fun responseException(e: OutException) = with(e) {
        response.status = httpStatus.value()
        Out(message)
    }

    /** 校验 post controller 方法实体参数 */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun paramException(e: MethodArgumentNotValidException) = e.bindingResult.toOut()

    /** 校验 get controller 方法实体参数 */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    fun paramException(e: BindException) = e.bindingResult.toOut()

    /** 校验 controller 方法非实体参数 */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun paramException(e: ConstraintViolationException) =
        Out(
            buildString {
                append(PARAMETER_ERROR.message)
                e.constraintViolations.forEach { append(it.message) }
            }
        )

    /** @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler.handleException */
    @ExceptionHandler(
        HttpRequestMethodNotSupportedException::class,
        HttpMediaTypeNotSupportedException::class,
        HttpMediaTypeNotAcceptableException::class,
        MissingPathVariableException::class,
        MissingServletRequestParameterException::class,
        ServletRequestBindingException::class,
        ConversionNotSupportedException::class,
        TypeMismatchException::class,
        HttpMessageNotReadableException::class,
        HttpMessageNotWritableException::class,
        MissingServletRequestPartException::class,
        NoHandlerFoundException::class,
        AsyncRequestTimeoutException::class,
        Throwable::class
    )
    fun exception(e: Exception): Out {
        val status =
            when (e) {
                is HttpRequestMethodNotSupportedException -> {
                    METHOD_NOT_ALLOWED
                }

                is HttpMediaTypeNotSupportedException -> {
                    UNSUPPORTED_MEDIA_TYPE
                }

                is HttpMediaTypeNotAcceptableException -> {
                    NOT_ACCEPTABLE
                }

                is MissingPathVariableException -> {
                    INTERNAL_SERVER_ERROR
                }

                is MissingServletRequestParameterException -> {
                    BAD_REQUEST
                }

                is ServletRequestBindingException -> {
                    BAD_REQUEST
                }

                is ConversionNotSupportedException -> {
                    INTERNAL_SERVER_ERROR
                }

                is TypeMismatchException -> {
                    BAD_REQUEST
                }

                is HttpMessageNotReadableException -> {
                    BAD_REQUEST
                }

                is HttpMessageNotWritableException -> {
                    INTERNAL_SERVER_ERROR
                }

                is MissingServletRequestPartException -> {
                    BAD_REQUEST
                }

                is NoHandlerFoundException -> {
                    NOT_FOUND
                }

                is AsyncRequestTimeoutException -> {
                    SERVICE_UNAVAILABLE
                }

                else -> {
                    INTERNAL_SERVER_ERROR
                }
            }
        response.status = status.value()
        e.printStackTrace()
        return Out(status.reasonPhrase)
    }

    companion object {
        /** BindingResult 转 Out */
        private fun BindingResult.toOut(): Out {
            val self = this
            val message = buildString {
                append("${PARAMETER_ERROR.message} ")
                // 获取类注解 @ScriptAssert 异常信息
                self.globalErrors.forEach { append("${it.defaultMessage}，") }
                // 获取类属性注解 @NotBlank、@Length 等异常信息
                self.fieldErrors.forEach { append("${it.field}：${it.defaultMessage}，") }
                // 删除最后一个 `，`
                setLength(length - 1)
            }
            return Out(message)
        }
    }
}
