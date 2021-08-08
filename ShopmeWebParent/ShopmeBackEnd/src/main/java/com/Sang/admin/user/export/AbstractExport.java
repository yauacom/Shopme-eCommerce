package com.Sang.admin.user.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

public class AbstractExport {

  public void setResponseHeader(
      HttpServletResponse response,
      String contentType,
      String extension)
      throws IOException {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    String timestamp = dateFormat.format(new Date());
    String fileName = "users_" + timestamp + extension;

    response.setContentType(contentType);

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=" + fileName;
    response.setHeader(headerKey, headerValue);
  }
}
