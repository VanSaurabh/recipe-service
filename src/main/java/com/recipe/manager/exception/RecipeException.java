package com.recipe.manager.exception;

import com.recipe.manager.exception.model.BaseException;
import java.io.Serial;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RecipeException {

  public static class NotFoundException extends BaseException {

    @Serial
    private static final long serialVersionUID = 3555714415375055302L;

    public NotFoundException(String msg) {
      super(msg);
    }
  }
}
