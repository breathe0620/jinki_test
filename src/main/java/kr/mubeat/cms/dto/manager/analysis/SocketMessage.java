package kr.mubeat.cms.dto.manager.analysis;

/**
 * Created by moonkyu.lee on 2017. 8. 27..
 */
public class SocketMessage {

  private String content;

  public SocketMessage() {
  }

  public SocketMessage(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }
}
