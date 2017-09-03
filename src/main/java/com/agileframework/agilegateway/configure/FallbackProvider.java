package com.agileframework.agilegateway.configure;

import com.agileframework.agilegateway.base.RETURN;
import com.google.common.base.Charsets;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Component
public class FallbackProvider implements ZuulFallbackProvider {
  @Override
  public String getRoute() {
    return "*";
  }

  @Override
  public ClientHttpResponse fallbackResponse() {
    return new ClientHttpResponse() {
      @Override
      public HttpStatus getStatusCode() throws IOException {
        return HttpStatus.BAD_REQUEST;
      }

      @Override
      public int getRawStatusCode() throws IOException {
        return HttpStatus.BAD_REQUEST.value();
      }

      @Override
      public String getStatusText() throws IOException {
        return HttpStatus.BAD_REQUEST.getReasonPhrase();
      }

      @Override
      public void close() {
      }

      @Override
      public InputStream getBody() throws IOException {
        HashMap<String,Object> map = new HashMap<>();
        map.put("head",RETURN.ERROR);
        return new ByteArrayInputStream((JSONObject.fromObject(map).toString()).getBytes(Charsets.UTF_8));
      }

      @Override
      public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
      }
    };
  }
}