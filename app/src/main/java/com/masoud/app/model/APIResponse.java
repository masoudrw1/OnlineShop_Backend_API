package com.masoud.app.model;

import com.masoud.app.model.enums.APIStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
   private String message;
   private APIStatus status;
   private T data;

}
