// ES 기능
$.fn.makeSearchComponent = function(type, result, e) {
    var $target =  $(this).parent().find('ul');

    $target.show();

    if($(this).outerWidth() < 200){
        $target.width(200);
        $target.offset({left:$(this).offset().left - (200 - $(this).outerWidth())});
    }else{
        $target.outerWidth($(this).outerWidth());
        $target.offset({left:$(this).offset().left});
    }

    if(e.which != 13 && e.which != 37 && e.which != 38 && e.which != 39 && e.which != 40)
        $target.empty();

    if (type === 'agency'){
      var param = result.data;
    } else if (type === 'album' || type === 'artist' || type === 'song') {
      var param = result.data.data;
    }

    if(param != null && param.length > 0) {
        if(e.which != 13 && e.which != 37 && e.which != 38 && e.which != 39 && e.which != 40){
            for(var i = 0; i < param.length; i++) {
                var str = "";
                if(type == 'album' || type == 'song') {

                    str += '<li class="list-group-item list-group-custom"><input type="hidden" id="id" value="' + param[i].id;
                    str += '"/><input type="hidden" id="name" value="'+ param[i].name;
                    str += '"/><span class="list-text li-font-big">' + param[i].name;
                    str += '</span><span class="li-font-small">ID: ' + param[i].id;
                    str += ' | MID: ';
                    str += param[i].originid
                    if(param[i].artistName === null){
                        str += ' | Artist: 없음</span></li>';
                    }else{
                        var arrayStr = param[i].artistName.split(',');
                        if(arrayStr.length <= 2)
                            str += ' | Artist: '+ param[i].artistName + '</span></li>';
                        else
                            str += ' | Artist: '+ arrayStr[0] + ',' + arrayStr[1] + ' 외 '+ (arrayStr.length-2) +'명</span></li>';
                    }
                    str += '<input type="hidden" id="mouseX" value="0"/>';
                    str += '<input type="hidden" id="mouseY" value="0"/>';

                    $target.append(str);

                } else if ( type == 'artist') {
                    str += '<li class="list-group-item list-group-custom"><input type="hidden" id="id" value="' + param[i].id;
                    str += '"/><input type="hidden" id="name" value="' + param[i].name;
                    str += '"/><span class="list-text li-font-big">' + param[i].name;
                    str += '</span><span class="li-font-small">ID: ' + param[i].id;
                    str += ' | MID: ' + param[i].originid;
                    str += '</span></li>';
                    str += '<input type="hidden" id="mouseX" value="0"/>';
                    str += '<input type="hidden" id="mouseY" value="0"/>';

                    $target.append(str);

                } else if (type == 'agency') {

                    str += '<li class="list-group-item list-group-custom"><input type="hidden" id="id" value="' + param[i].agencyId;
                    str += '"/><input type="hidden" id="name" value="' + param[i].agencyName;
                    str += '"/><span class="list-text li-font-big">' + param[i].agencyName;
                    str += '</span><span class="li-font-small">ID: ' + param[i].agencyId;
                    str += '</span></li>';
                    str += '<input type="hidden" id="mouseX" value="0"/>';
                    str += '<input type="hidden" id="mouseY" value="0"/>';

                    $target.append(str);
                }
            }
        }

        keyupScrollEvent($target, e.which);

        if(e.which == 13 && $target.find('li').is('.active') == true){
            var targetValue = $target.find('.active');
            var top = $target.parent();

            top.find('input[name=searchInputName]').blur();
            top.find('input[name=searchInputName]').val(targetValue.find('#name').val());
            top.find('input[name=searchInputId]').val(targetValue.find('#id').val());

            $target.css('display','none');

            var evt = jQuery.Event('onAutoLayoutSelected', {
                id: targetValue.find('#id').val(),
                name: targetValue.find('#name').val()
            });

            top.find('input[name=searchInputName]').trigger(evt);
        }

        $target.unbind().bind('keyup').delegate('li', 'click', function () {
                var top = $(this).parent().parent();

                top.find('input[name=searchInputName]').val($(this).find('#name').val());
                top.find('input[name=searchInputId]').val($(this).find('#id').val());
                top.find('ul').empty();
                top.find('ul').css('display','none');

                var evt = jQuery.Event('onAutoLayoutSelected', {
                    id: $(this).find('#id').val(),
                    name: $(this).find('#name').val()
                });

                top.find('input[name=searchInputName]').trigger(evt);
        });

        $(document).unbind().bind('mousemove', function(e) {
            $("#mouseX").val(e.pageX);
            $("#mouseY").val(e.pageY);
        });
        
    } else {
      $target.empty().append('<li class="list-group-item"><span class="left-text">검색 결과가 없습니다.</span></li><input type="hidden" id="mouseX" value="0"/><input type="hidden" id="mouseY" value="0"/>');
    }
    $(this).inputFocusOut();
    cms.lightBox();
};

$.fn.inputFocusOut = function(){
  $(this).focusout(function(){

      var x = $("#mouseX").val();
      var y = $("#mouseY").val();
      var offset = $(this).parent().find('ul').offset();
      var left = offset.left;
      var right = offset.left + $(this).parent().find('ul').width();
      var top = offset.top;
      var bottom = offset.top + $(this).parent().find('ul').height();

      if(left > x || right < x || top > y || bottom < y) {
        $(this).parent().find('ul').empty();
        $(this).parent().find('ul').css('display','none');
        $(this).val('');
      }
  });
};

$.fn.checkOriginId = function (url) {
    var $targetValue = $(this).val();
    $(this).on('change', function () {
        var $target = $(this);
        $.ajax({
            url: url + $target.val(),
            type: "GET",
            success: function (result) {
                if(result === false && $targetValue !== $target.val()){
                    alert('중복된 MID값이 존재합니다.');
                    $target.removeClass().addClass('origin-check-error').parent().append('<span class="origin-check-list">중복된 MID값 존재</span>');
                }else{
                    $target.removeClass().addClass('origin-check-success').parent().find('span').remove();
                }
            },
            complete: function () {
                $target.focus();
            }
        });
    });
};

$.fn.makeTableInfo = function (type, result) {
    var target = $(this);
    var str = '';
    var arrayCheck = [];
    var duplicateCheck = true;

    if(type == 'artist'){
        $("td[name=checkArtistId]").each(function(index){
            arrayCheck[index] = $(this).text();
        });
    }else if(type == 'song'){
        $("td[name=checkSongId]").each(function(index){
            arrayCheck[index] = $(this).text();
        });
    }

    for(var i = 0; i < arrayCheck.length; i++){
        if(type == 'artist'){
            if(arrayCheck[i] == result.data.artistId){
                alert('이미 중복된 아티스트가 존재합니다.');
                duplicateCheck = false;
            }
        }else if(type == 'song'){
            if(arrayCheck[i] == result.data.songId){
                alert('이미 중복된 곡이 존재합니다.');
                duplicateCheck = false;
            }
        }
    }
    if(duplicateCheck == true){
        if(type == 'artist'){

            str += '<table class="table table-bordered table-condensed table-detail th-text-center"><tr class="table-two-group-width">';
            str += '<th>아티스트ID</th><td name="checkArtistId">'+result.data.artistId+'</td>';

            if(result.data.originId != undefined)
                str += '<th>아티스트MID</th><td>'+ result.data.originId +'<button type="button" class="btn btn-xxs btn-danger btn-clip-info-delete"><i class="fa fa-times"></i></button></td></tr>';
            else
                str += '<th>아티스트MID</th><td><button type="button" class="btn btn-xxs btn-danger btn-clip-info-delete"><i class="fa fa-times"></i></button></td></tr>';

            str += '<tr><th>아티스트명 <span class="red">(한글)</span></th><td>'+result.data.artistName+'</td>';
            str += '<th>아티스트명 <span class="red">(영문)</span></th>';

            if(result.data.artistNameEn != undefined)
                str += '<td name="clipArtistNameEn">'+result.data.artistNameEn+'</td></tr>';
            else
                str += '<td name="clipArtistNameEn"></td></tr>';

            str += '<tr><th>아티스트 대문 이미지</th> <td colspan="3" class="img-href">';
            str += '<a data-toggle="modal" data-target="#lightbox">';

            if(result.data.mainImgUrl != null)
                str += '<img alt="" src="'+result.data.mainImgUrl+'" class="lightBox-Url"/>'+result.data.mainImgUrl;

            str += '</a></td></tr><tr><th>아티스트 프로필 이미지</th>';
            str += '<td colspan="3" class="img-href">';
            str += '<a data-toggle="modal" data-target="#lightbox">';

            if(result.data.profileImgUrl != null)
                str += '<img alt="" src="'+result.data.profileImgUrl+'" class="lightBox-Url"/>'+result.data.profileImgUrl;

            str += '</a></td></tr></table>';

            target.append(str);

        } else if (type == 'song'){
            str += '<table class="table table-bordered table-condensed table-detail th-text-center">';
            str += '<tr class="table-two-group-width">';
            str += '<th>곡ID</th>';
            str += '<td name="checkSongId">'+result.data.songId+'</td>';
            str += '<th>곡MID</th>';

            if(result.data.originId != undefined)
                str += '<td>'+ result.data.originId +'<button type="button" class="btn btn-xxs btn-danger btn-clip-info-delete"><i class="fa fa-times"></i></button></td></tr>';
            else
                str += '<td><button type="button" class="btn btn-xxs btn-danger btn-clip-info-delete"><i class="fa fa-times"></i></button></td></tr>';

            str += '<tr><th>곡명 <span class="red">(한글)</span></th><td>'+result.data.songName+'</td>';
            str += '<th>곡명 <span class="red">(영문)</span></th>';

            if(result.data.songNameEn != undefined)
                str += '<td name="clipSongNameEn">'+result.data.songNameEn+'</td></tr>';
            else
                str += '<td name="clipSongNameEn"></td></tr>';

            if(result.data.publishDt != undefined)
                str += '<tr><th>발매일</th><td>'+moment(result.data.publishDt).format("YYYY-MM-DD")+'</td>';
            else
                str += '<tr><th>발매일</th><td></td>';

            str += '<th>활동장르</th><td>'+matchingGenre(result.data.genreId)+'</td></tr>';
            str += '<tr><th>앨범 이미지</th><td colspan="3" class="img-href"><a data-toggle="modal" data-target="#lightbox">';

            if(result.data.albumImgUrl != null)
                str += '<img alt="" src="'+result.data.albumImgUrl+'" class="lightBox-Url"/>'+result.data.albumImgUrl;

            str += '</a></td></tr></table>';
            target.append(str);
        }
    }
    $('.infoDelete').tableDelete();
    cms.lightBox();
};
// datepicker parsley 처리
$.fn.datepickerValidate = function(target) {
    $(target).on('change',function () {
        console.log(1);
        if($(target).val().length > 0 && $(target).parsley().validate()){
            $(target).removeClass('parsley-error').addClass('parsley-success');
        }else {
            $(target).removeClass('parsley-success').addClass('parsley-error');;
        }
    });
};
// table 삭제
$.fn.tableDelete = function () {
    $(this).on('click',function () {
        var target = $(this).parent().parent().parent().parent();
        target.remove();
    });
};
// radio 체크시 타입변경
$.fn.radioCheckType = function(target){
  $(this).on('change', function() {
    if($('input:radio[name=radioSearch]:checked').val() == 'originId'){
      $(target).attr('type','number');
    }else{
      $(target).attr('type','text');
    }
  });
};
// 리스트 상세보기
$.fn.locationListView = function(target, url){
  $(this).on('click',function(){
      window.sessionStorage.setItem('url', window.location.href);
      var str = $(this).parent().find('td' + target).html();
      console.log("url : "+url);
      console.log("target : "+target);
      console.log("str : "+str);
      var detailUrl = url + str;
      console.log("detailUrl : "+detailUrl);
      window.location.href = detailUrl;
  });
};
// 해당 url 이동
$.fn.locationMove = function(url){
  $(this).on('click',function(){
    window.location.href = url;
  });
};
// 뒤로가기
$.fn.locationBack = function(url){
  $(this).on('click',function(){
      window.location.href = url;
  });
};
// 새로고침
$.fn.locationReload = function(){
  $(this).on('click',function(){
    location.reload();
  });
};
// button 삭제
$.fn.btnDelete = function(){
    $(this).on('click',function(){

        var safeElement = $(this).parent().parent().find('input[name=searchInputName]');
        $(this).parent().prev().remove().end(); // input 삭제
        $(this).parent().remove().end(); // button 삭제
        redrawInput(safeElement);
    });
};
// 프로그램명 노출
$.fn.programListView = function () {
    var corporatorsName = $(this).find("option:selected").text();
    var options = {
        'type'        : 'GET',
        'url'         : '/meta/programs/'+corporatorsName,
        "callback" : function(result){
            $('#programCategory').empty().append('<option value="" selected>프로그램명</option>');
            for(var i = 0; i < result.data.length; i++){
                $('#programCategory').append('<option value="'+result.data[i].programId+'">'+result.data[i].programTitle+'</option>');
            }
            if($('#program').val() != ''){
                $('#programCategory').val($('#program').val());
                $('#programCategory').parent().find(".select2-selection__rendered").empty().append($('#programCategory').find('option:selected').text());
            }
        }
    };
    cms.ajaxCall(options);
};
// 특정 특수문자 체크
$.fn.getWordCheck = function(e, callback) {

    if($(this).val().trim() == 0 && e.which == 32){
        alert('첫글자에 공백은 사용될 수 없습니다.');
        $(this).val('');
    } else if($(this).val().length == 1 && wordCheck($(this).val()) == false){
        alert("첫글자에 특수문자( # % \\ / ; \" . ) 는 사용될 수 없습니다.");
        $(this).val('');
    } else if(wordCheck($(this).val())){
        if ($(this).val().trim().length == 0){
            $(this).parent().find('ul').css('display','none');
        }else if(e.which == 8 && $(this).next().val() != ''){
            $(this).val('');
            $(this).next().val('');
        }else if($(this).val().length > 0) {
            callback($(this).val().toLowerCase());
        }
    }
};

$.fn.serializeObject = function () {
    var obj = null;
    try {
        if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
            var arr = this.serializeArray();
            if (arr) {
                obj = {};
                jQuery.each(arr, function () {
                    obj[this.name] = this.value;
                });
            }
        }
    } catch (e) {
        alert(e.message);
    } finally {
    }
    return obj;
};

function timerAfterPageReload()  {
    setInterval(function () {
        location.reload();
    }, 2500);
}

// QueryString 정규표현식 검색
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url); // exec() 정규표현식과 일치하는 문자열들을 배열에 담아서 리턴

    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function keyupScrollEvent(target, word) {
    var active = target.find('.active'),
        height = $(active).outerHeight(),
        top = target.scrollTop(),
        totalHeight = 0,
        prevAllHeight = (target.find('.active').prevAll('li').length * height) + height,
        nextAllHeight = (target.find('.active').nextAll('li').length * height) + height;

    $("ul li").each(function() {
        totalHeight += $(this).outerHeight(true);
    });

    if(word == 38) {
        // Active
        if(target.find('li').is('.active') == false){
            target.find('li:last').addClass('active');
        }else if(target.find('li').is('.active') == true){

            if(target.find('li:first').is('.active')){
                target.find('.active').removeClass('active');
                target.find('li:last').addClass('active');
            } else
                target.find('.active').removeClass('active').prev().prev().prev().addClass('active');
        }
        // Scroll
        if(target.find('li:last').is('.active'))
            target.scrollTop(totalHeight);
        else if(nextAllHeight > target.height())
            target.scrollTop(top - height);

    } else if(word == 40){
        // Active
        if(target.find('li').is('.active') == false){
            target.find('li:first').addClass('active');
        }else if(target.find('li').is('.active') == true){

            if(target.find('li:last').is('.active')){
                target.find('.active').removeClass('active');
                target.find('li:first').addClass('active');
            }else
                target.find('.active').removeClass('active').next().next().next().addClass('active');
        }
        // Scroll
        if(target.find('li:first').is('.active'))
            target.scrollTop(0);
        else if(prevAllHeight > target.height())
            target.scrollTop(top + height);
    }
}
function redrawInput(obj) {

    var arrBox = obj.parent().find(':button[name=btnBox]');
    var totalBoxWidth = 0;

    obj.parent().find('br').remove();

    for(var i = 0; i < arrBox.length; i++) {

        var width = arrBox.eq(i).outerWidth(),
            marginLeft = parseInt(arrBox.eq(i).css('marginLeft')) + 2,
            marginRight = parseInt(arrBox.eq(i).css('marginRight')) + 2;

        totalBoxWidth += width + marginLeft + marginRight;

        if(obj.parent().width() < totalBoxWidth + 100){
            if(arrBox.eq(i).next('br').length < 1)
                arrBox.eq(i).after('<br>');
            totalBoxWidth = 0;
        }
    }
    if(obj.parent().width() > totalBoxWidth)
        obj.outerWidth(obj.parent().width() - totalBoxWidth);
}

// 새창 띄우기
function windowOpen(url) {
    window.open(url,'CmsVlending','width=854, height=480, toolbar=no, menubar=no, scrollbars=no, resizable=no');
}

function transcodeSumbit (array) {
    var options = {
        'type'     : 'PUT',
        'url'      : '/meta/acquisition/transcode',
        'data'     : $.param({ "id": array }, true),
        'callback'    : function(){
            window.location.href = decodeURIComponent(document.location.href);
        }
    };

    if(confirm('요청하시겠습니까?'))
        cms.ajaxCall(options);
}
function wordCheck(string) {

    var stringRegx = /[.;\\/#%"]/g;
    var isValid = true;

    if(stringRegx.test(string)){
        isValid = false;
    }
    return isValid;
}

function makeImageFile(src) {
    var byteString = atob(src.split(',')[1]),
        arrayBufferString = new ArrayBuffer(byteString.length),
        unit8ArrayString = new Uint8Array(arrayBufferString);

    for (var i = 0; i < byteString.length; i++) {
        unit8ArrayString[i] = byteString.charCodeAt(i);
    }

    return arrayBufferString;
}

function htmlEntityEnc(str){
    if(str == "" || str == null){
        return str;
    }
    else{
        return str.replace("&", "&amp;").replace("#", "&#35;").replace("<", "&lt;").replace(">", "&gt;").replace(/"/g, "&quot;").replace('\\', "&#39;").replace('%', "&#37;").replace('(', "&#40;").replace(')', "&#41;").replace('+', "&#43;").replace('/', "&#47;").replace('.', "&#46;");
    }
}

function htmlEntityDec(str){
    if(str == "" || str == null){
        return str;
    }
    else{
        return str.replace("&amp;", "&").replace("&#35;", "#").replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", /"/g).replace("&#39;", '\\').replace("&#37;", '%').replace("&#40;", '(').replace("&#41;", ')').replace("&#43;", '+').replace("&#47;", '/').replace("&#46;", '.');
    }
}

var cms = {

    ajaxCall: function (options) {

        var settings = $.extend({}, options);
        $.ajax({
            type: settings.type,
            url: settings.url,
            data: settings.data,
            contentType: settings.contentType,
            dataType: settings.dataType,
            processData: settings.processData,
            beforeSend : function () {
                $.blockUI({
                    css: {
                        border: 'none',
                        padding: '7px 4px 2px 4px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: .5,
                        color: '#fff',
                        width: '200px',
                        left: '45%'
                    },
                    message:'<h3><i class="fa fa-spinner fa-spin"></i> loading...</h3>'
                });
            },
            success: function (result) {
                if(settings.useTimer === undefined || settings.useTimer === null || settings.useTimer === false){
                    $.unblockUI();
                }
                if (settings.type === "PUT" || settings.type === "POST") {
                    if (result.code === "0000") {
                        settings.callback(result);
                    } else {
                        alert("[" + result.code + "] " + result.msg);
                    }
                } else if (settings.type === "DELETE") {
                    if (result.code === "0000") {
                        settings.callback(result);
                    } else {
                        alert("[" + result.code + "] " + result.msg);
                    }
                } else {
                    settings.callback(result);
                }
            },
            error: function (xhr, option, error) {
                $.unblockUI();
                if (xhr.status !== 0 && xhr.status !== 200) {
                    alert(xhr.status + "<-- error Code");
                }
            }
        });
    },

    ajaxSearch: function (options) {
        var settings = $.extend({}, options);

        $.ajax({
            type: "GET",
            url: settings.url,
            success: function (result) {
                settings.callback(result);
            },
            error: function (xhr) {
                if (xhr.status !== 0 && xhr.status !== 200) {
                    alert(xhr.status + "<-- error Code");
                }
            }
        });
    },

    // 파일 경로 읽기
    readURL: function (input, target) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $(target).attr('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
        }
    },

    // 파일 미리보기 및 파일 제한
    ImgAcceptPreview: function (targetImg, targetView, callback) {
        $(targetImg).on('change', function () {
            if ($(targetImg).val() != "") {
                var ext = $(targetImg).val().split('.').pop().toLowerCase();
                if ($.inArray(ext, ['png', 'jpg']) == -1) {
                    alert('png, jpg파일만 업로드 할수 있습니다.');
                    $(targetImg).val("");
                    return false;
                }
            }
            cms.readURL(this, targetView);
            callback();
            $(targetImg).val('');
        });
    },

    select: function (selectId, addCallback) {

        cms.select.sID = selectId;
        cms.select.callback = addCallback;

        cms.select.sID.select2({
            "placeholder": "유형 조회",
            "minimumResultsForSearch": Infinity
        });

        cms.select.sID.on('change',function () {
            $(this).parsley().validate();
        });

        bindSelectClickEvent(cms.select.callback);
    },

    lightBox: function () {
        var $lightbox = $(document).find('#lightbox');
        $(document).on('click', '[data-target="#lightbox"]', function (event) {

            var $img = $(this).find('img'),
                src = $img.attr('src'),
                alt = $img.attr('alt'),
                css = {
                    'maxWidth': $(window).width() - 100,
                    'maxHeight': $(window).height() - 100
                };
            $lightbox.find('.close').addClass('hidden');
            $lightbox.find('img').attr('src', src);
            $lightbox.find('img').attr('alt', alt);
            $lightbox.find('img').css(css);
            $lightbox.modal();
        });

        $lightbox.on('shown.bs.modal', function (e) {
            var $img = $lightbox.find('img');
            $lightbox.find('.modal-dialog').css({'width': $img.width()});
            $lightbox.find('.close').removeClass('hidden');
        });
    }
};

cms.select.callback = null;
cms.select.sID = null;
cms.select.validate = function(firstValue, secondValue) {

    cms.select.sID.select2({
        'placeholder' : '유형 조회',
        'minimumResultsForSearch': Infinity,
        'data' : [{id: firstValue, text: secondValue}]
    });

    bindSelectClickEvent(cms.select.callback);
};
function bindSelectClickEvent(callback) {
  $('[name=createTypeSelect]').parent().find('.select2-selection__rendered, .select2-selection__arrow').one('click', function(){
    if($('[name=createTypeSelect]').parent().find('.select2-selection__rendered, .select2-selection__arrow').children().last().text() != '추가') {
      var str = '<form id="formType" class="typeInput" data-parsley-validate>';
        str +=      '<div class="col-md-12 genreForm-input input-group tableParsley">';
        str +=          '<input type="text" class="form-control genreInput" id="resultName" placeholder="유형명 (최소 1글자)" data-parsley-required="true" data-parsley-minlength="1">';
        str +=          '<span class="input-group-btn"><button type="submit" class="btn btn-info" id="btnType"><strong>추가</strong></button></span>';
        str +=      '</div>';
        str += '</form>';
      $('.select2-dropdown--below, .select2-dropdown--above').append(str);
      $('#btnType').on('click',function() {
        if($('#formType').parsley({
          errorsWrapper: '<span class="parsley-errors-list"></span>',
          errorTemplate: '<span style="color:red;"></span>'
        }).validate()) {
          callback($('#resultName').val());
        }
        return false;
      });
    }
  });
}