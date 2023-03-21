package com.leijendary.spring.template.notification.core.util

import com.leijendary.spring.template.notification.core.util.SpringContext.Companion.getBean
import io.micrometer.tracing.TraceContext
import io.micrometer.tracing.Tracer
import org.slf4j.MDC

const val HEADER_TRACE_PARENT = "traceparent"

private const val MDC_TRACE_ID = "traceId"
private const val MDC_SPAN_ID = "spanId"

private val tracer = getBean(Tracer::class)

object Tracing {
    fun get(): TraceContext = tracer.nextSpan().context()

    fun log(traceParent: String, function: () -> Unit) {
        val trace = traceParent.split("-")

        MDC.put(MDC_TRACE_ID, trace[1])
        MDC.put(MDC_SPAN_ID, trace[2])

        function()

        MDC.remove(MDC_TRACE_ID)
        MDC.remove(MDC_SPAN_ID)
    }
}