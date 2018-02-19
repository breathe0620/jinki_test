package kr.mubeat.cms.dto.meta.clip;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 7. 17..
 */
public class TranscodeDTO implements Serializable {

    private static final long serialVersionUID = 6676279153082041900L;

    @QueryProjection
    public TranscodeDTO(
            Long transcodeOrEnableDone,
            Long enableMapped,
            Long allDone,
            Long readyToTranscode,
            Long uploadFails,
            Long transcodeFails,
            Long transcodedMusic,
            Long transcodedEntertainment
    ) {
        this.transcodeOrEnableDone = transcodeOrEnableDone;
        this.enableMapped = enableMapped;
        this.allDone = allDone;
        this.readyToTranscode = readyToTranscode;
        this.uploadFails = uploadFails;
        this.transcodeFails = transcodeFails;
        this.transcodedMusic = transcodedMusic;
        this.transcodedEntertainment = transcodedEntertainment;
    }

    private Long transcodeOrEnableDone;
    private Long enableMapped;
    private Long allDone;
    private Long readyToTranscode;
    private Long uploadFails;
    private Long transcodeFails;
    private Long transcodedMusic;
    private Long transcodedEntertainment;

    public Long getTranscodeOrEnableDone() {
        return transcodeOrEnableDone;
    }

    public void setTranscodeOrEnableDone(Long transcodeOrEnableDone) {
        this.transcodeOrEnableDone = transcodeOrEnableDone;
    }

    public Long getEnableMapped() {
        return enableMapped;
    }

    public void setEnableMapped(Long enableMapped) {
        this.enableMapped = enableMapped;
    }

    public Long getAllDone() {
        return allDone;
    }

    public void setAllDone(Long allDone) {
        this.allDone = allDone;
    }

    public Long getReadyToTranscode() {
        return readyToTranscode;
    }

    public void setReadyToTranscode(Long readyToTranscode) {
        this.readyToTranscode = readyToTranscode;
    }

    public Long getUploadFails() {
        return uploadFails;
    }

    public void setUploadFails(Long uploadFails) {
        this.uploadFails = uploadFails;
    }

    public Long getTranscodeFails() {
        return transcodeFails;
    }

    public void setTranscodeFails(Long transcodeFails) {
        this.transcodeFails = transcodeFails;
    }

    public Long getTranscodedMusic() {
        return transcodedMusic;
    }

    public void setTranscodedMusic(Long transcodedMusic) {
        this.transcodedMusic = transcodedMusic;
    }

    public Long getTranscodedEntertainment() {
        return transcodedEntertainment;
    }

    public void setTranscodedEntertainment(Long transcodedEntertainment) {
        this.transcodedEntertainment = transcodedEntertainment;
    }
}
