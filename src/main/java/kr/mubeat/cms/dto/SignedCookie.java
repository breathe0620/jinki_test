package kr.mubeat.cms.dto;

/**
 * Created by moonkyu.lee on 2017. 8. 29..
 */
public class SignedCookie {

    private String policy;
    private String signature;
    private String keyPairId;

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getKeyPairId() {
        return keyPairId;
    }

    public void setKeyPairId(String keyPairId) {
        this.keyPairId = keyPairId;
    }
}
