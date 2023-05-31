package com.udacity.webcrawler.profiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.Objects;
import java.util.Optional;

/**
 * A method interceptor that checks whether {@link Method}s are annotated with the {@link Profiled}
 * annotation. If they are, the method interceptor records how long the method invocation took.
 */
final  class ProfilingMethodInterceptor implements InvocationHandler {

  private final Clock clock;
  private final Object delegate;

  ProfilingState profilingState ;

  // TODO: You will need to add more instance fields and constructor arguments to this class.
  ProfilingMethodInterceptor(Clock clock, Object delegate, ProfilingState state) {
    this.delegate = delegate;
    this.profilingState = state;
    this.clock = Objects.requireNonNull(clock);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object op;
    Instant start = null;

      try {
       start= clock.instant();
       op = method.invoke(delegate,args);

      }catch (InvocationTargetException invocationTargetException){
        throw invocationTargetException.getTargetException();

      } catch (IllegalAccessException illegalAccessException) {
          throw new RuntimeException(illegalAccessException);
      }finally {
          Instant end = clock.instant();
          profilingState.record(delegate.getClass(), method, Duration.between(start, end));
        }


      return op;

  }
}




// TODO: This method interceptor should inspect the called method to see if it is a profiled
//       method. For profiled methods, the interceptor should record the start time, then
//       invoke the method using the object that is being profiled. Finally, for profiled
//       methods, the interceptor should record how long the method call took, using the
//       ProfilingState methods.
//    }
