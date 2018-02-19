<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <link href="/css/custom.css" rel="stylesheet">
    <link rel="stylesheet" href="/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/vendor/cropper-master/src/css/cropper.min.css">
    <script src="/vendor/jquery/jquery.min.js"></script>
    <script src="/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="/vendor/cropper-master/src/js/cropper.min.js"></script>
    <script src="/js/custom.js"></script>
</head>
<body>
<div class="row custom-image-crop" style="margin-left: 100px; margin-right: 100px; margin-top: -20px;">
    <div class="col-md-12">
        <h3 class="page-header" style="margin-bottom: 5px;">Image Crop!</h3>
        <div class="row">
            <div class="col-md-12">
                <div class="col-md-6">
                    <div class="btn-group">
                        <button type="button" name="btnRatio" id="aspectRatio_1611" class="btn btn-primary" disabled>16:11</button>
                        <button type="button" name="btnRatio" id="aspectRatio_169" class="btn btn-primary">16:9</button>
                        <button type="button" name="btnRatio" id="aspectRatio_43" class="btn btn-primary">4:3</button>
                        <button type="button" name="btnRatio" id="aspectRatio_23" class="btn btn-primary">2:3</button>
                        <button type="button" name="btnRatio" id="aspectRatio_11" class="btn btn-primary">1:1</button>
                    </div>
                    <div class="btn-group">
                        <button type="button" id="cropSizeFree" class="btn btn-primary">Free</button>
                        <button type="button" id="cropReset" class="btn btn-primary">Reset</button>
                    </div>
                    <div class="btn-group">
                        <label title="Upload image file" for="imgInput" class="btn btn-info" style="width:58px;">
                            <input type="file" class="input-file" id="imgInput" name="imgInput" style="display: none;">Open
                        </label>
                        <button type="button" id="doCrop" class="btn btn-success">Save</button>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <h4><strong>Origin image</strong></h4>
                <div class="image-crop">
                    <img src="" id="imgSrc" name="imgSrc">
                </div>
                <div class="form-group img-preview-after-div text-center">
                    <label for="imageWidth">Image Width</label>
                    <input type="text" name="originImage" id="imageWidth" value="" readonly/>
                    <label for="imageHeight">Image Height</label>
                    <input type="text" name="originImage" id="imageHeight" value="" readonly/>
                </div>
            </div>
            <div class="col-md-6">
                <h4><strong>Preview image</strong></h4>
                <div class="img-preview image-crop"
                     style="overflow: hidden; width: 100% !important; height: 50% !important;"></div>
                <div class="form-group img-preview-after-div text-center">
                    <label for="previewWidth">PreviewWidth</label>
                    <input type="text" name="setPreview" id="previewWidth" value=""
                           />
                    <label for="previewHeight">PreviewHeight</label>
                    <input type="text" name="setPreview" id="previewHeight" value=""
                           disabled/>
                </div>
                <div class="alert alert-success col-md-offset-1 col-md-10">
                    <li>이미지 사이즈 조정이 필요한 경우 PreviewWidth의 값을 수정하시길 바랍니다.</li>
                    <li>입력시 선택한 비율로 사이즈가 저장됩니다.</li>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    // 정책이 필요! 수정필요함
    $('#previewWidth').on('keyup',function () {

        $('#previewHeight').prop('disabled',true);

       if($(this).val().length >= 3){
           var StrRatio = $('button[disabled]').text().split(':');
           var ratioWidth = parseInt(StrRatio[0]),
                ratioHeight = parseInt(StrRatio[1]),
                resizeWidth  = parseInt($(this).val()),
                resizeHeight;

           resizeHeight = (resizeWidth / ratioWidth) * ratioHeight;
           $('#previewHeight').val(parseInt(resizeHeight));
       }
    });

    function imageCropStart(target, src, type) {
        target.attr('src', src).cropper({
            viewMode : 1,
            aspectRatio: type,
            zoomOnWheel: false,
            preview: ".img-preview",
            center: true,
            autoCropArea: 1,
            crop: function (e) {
                var canvasData = $('#imgSrc').cropper('getCanvasData');
                $('#imageWidth').val(canvasData.naturalWidth);
                $('#imageHeight').val(canvasData.naturalHeight);
                $('#previewWidth').val(parseInt(e.width));
                $('#previewHeight').val(parseInt(e.height));
            }
        }).cropper('reset', true).cropper('replace', src);
    }

    var $img = $('#imgSrc'),
        targetImg = $(opener.document).find('#cropImageTarget').val(),
        targetType = $(opener.document).find('#cropImageType').val(),
        type;

    if (targetType == 'album')
        type = 1;
    else if (targetType == 'artist')
        type = 16 / 11;

    $('#imgInput').on('change', function () {

        if ($('#imgInput').val() != "") {
            var ext = $('#imgInput').val().split('.').pop().toLowerCase();
            if ($.inArray(ext, ['png', 'jpg']) == -1) {
                alert('png, jpg파일만 업로드 할수 있습니다.');
                $('#imgInput').val("");
            }
        }
        if (this.files && this.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                imageCropStart($img, e.target.result, type);
            };
            reader.readAsDataURL(this.files[0]);
        }
        $('#imgInput').val('');
    });

    imageCropStart($img, $(opener.document).find(targetImg).attr('src'), type);

    $('#cropSizeFree').on('click', function () {
        $img.cropper('setAspectRatio', 0);
    });

    $('[name=btnRatio]').on('click', function () {
        var StrRatio = $(this).text().split(':');
        var ratio = parseInt(StrRatio[0]) / parseInt(StrRatio[1]);

        $img.cropper('setAspectRatio', ratio);

        $(this).prop('disabled',true);
        $('[name=btnRatio]').not(this).prop('disabled',false);

    });

    $('#cropReset').on('click', function () {
        $img.cropper('reset', true);
    });

    $('#doCrop').on('click', function () {
        var resizeWidth = $('#previewWidth').val(),
            resizeHeight = $('#previewHeight').val();

        if(resizeWidth != ''){
            if(resizeWidth >= 300)
                var cropAfterImage = $('#imgSrc').cropper('getCroppedCanvas',{'width':resizeWidth, 'height':resizeHeight}).toDataURL('image/png');
            else{
                alert('300px 이상으로 입력바랍니다.');
                return false;
            }
        }else{
            var cropAfterImage = $('#imgSrc').cropper('getCroppedCanvas').toDataURL('image/png');
        }

        $(opener.document).find(targetImg).attr('src', cropAfterImage);
        window.self.close();
    });
</script>
</body>
</html>