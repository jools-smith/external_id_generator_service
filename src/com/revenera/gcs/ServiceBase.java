package com.revenera.gcs;

import com.flexnet.external.type.*;
import com.revenera.gcs.utils.Log;
import com.revenera.gcs.utils.Utils;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;


public abstract class ServiceBase {

  protected final Log logger = Log.create(this.getClass());

  protected ServiceBase() {
    this.logger.in();
  }


  public Function<Throwable, com.flexnet.external.type.SvcException> serviceException = (throwable) -> new SvcException() {
    {
      this.setMessage(Utils.frameDetails(Thread.currentThread().getStackTrace()[3]));
      
      this.setName(throwable.getClass().getName());
    }
  };
}