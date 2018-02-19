/**
 * Created by doohwan.yoo on 2017. 4. 18..
 */
$(document).ready(function(){

    $('#signupForm').parsley();

    // $('#inputName').attr('data-parsley-required-message', "내용을 입력하셔야 합니다");

    $('#signupForm').submit(function(event){
        event.preventDefault();

        var $form = $(this);
        var url = $form.attr('action');
        var formData = $form.serialize();

        $.ajax({
            url: url,
            type: "POST",
            data: formData,
            success: function (result) {
                window.location.href = '/';
            },
            error: function(request, status, error){
                alert("request:"+request + "/ status :" + status + "/ error :"+ error);
            }
        });
    });
});
function init_sidebar() {
    var a = function () {
        $RIGHT_COL.css("min-height", $(window).height());
        var a = $BODY.outerHeight(), b = $BODY.hasClass("footer_fixed") ? -10 : $FOOTER.height(),
            c = $LEFT_COL.eq(1).height() + $SIDEBAR_FOOTER.height(), d = a < c ? c : a;
        d -= $NAV_MENU.height() + b, $RIGHT_COL.css("min-height", d)
    };

    $SIDEBAR_MENU.find("a").on("click", function (b) {
        var c = $(this).parent();
        c.is(".active") ? (c.removeClass("active active-sm"), $("ul:first", c).slideUp(function () {
            a()
        })) : (c.parent().is(".child_menu") ? $BODY.is(".nav-sm") && ($SIDEBAR_MENU.find("li").removeClass("active active-sm"), $SIDEBAR_MENU.find("li ul").slideUp()) : ($SIDEBAR_MENU.find("li").removeClass("active active-sm"), $SIDEBAR_MENU.find("li ul").slideUp()), c.addClass("active"), $("ul:first", c).slideDown(function () {
            a()
        }))
    }), $MENU_TOGGLE.on("click", function () {
        $BODY.hasClass("nav-md") ? ($SIDEBAR_MENU.find("li.active ul").hide(), $SIDEBAR_MENU.find("li.active").addClass("active-sm").removeClass("active")) : ($SIDEBAR_MENU.find("li.active-sm ul").show(), $SIDEBAR_MENU.find("li.active-sm").addClass("active").removeClass("active-sm")), $BODY.toggleClass("nav-md nav-sm"), a()
    }),
        $SIDEBAR_MENU.find("a").each(function () {
            var length = window.location.pathname.split('/').length;
            var check = window.location.pathname;

            for(var i = 0; i < length; i ++) {
                if($(this).attr('href') === check){
                    $SIDEBAR_MENU.find('a[href="' + check + '"]').parent("li").addClass("current-page").parents("ul").slideDown(function () {
                        a();
                    }).parent().addClass("active"), a();
                    return false;
                }
                if(check.includes("detail") == true || check.includes("create") == true)
                    check = check.substr(0,check.lastIndexOf('/'));
            }
        });
}

var CURRENT_URL = window.location.href.split("#")[0].split("?")[0],
    $BODY = $("body"), $MENU_TOGGLE = $("#menu_toggle"),
    $SIDEBAR_MENU = $("#sidebar-menu"), $SIDEBAR_FOOTER = $(".sidebar-footer"), $LEFT_COL = $("#leftSide"),
    $RIGHT_COL = $(".right_col"), $NAV_MENU = $(".nav_menu"), $FOOTER = $("footer"), randNum = function () {
        return Math.floor(21 * Math.random()) + 20
    };
var originalLeave = $.fn.popover.Constructor.prototype.leave;
$.fn.popover.Constructor.prototype.leave = function (a) {
    var c, d,
        b = a instanceof this.constructor ? a : $(a.currentTarget)[this.type](this.getDelegateOptions()).data("bs." + this.type);
    originalLeave.call(this, a), a.currentTarget && (c = $(a.currentTarget).siblings(".popover"), d = b.timeout, c.one("mouseenter", function () {
        clearTimeout(d), c.one("mouseleave", function () {
            $.fn.popover.Constructor.prototype.leave.call(b, b)
        })
    }))
}, $("body").popover({
    selector: "[data-popover]",
    trigger: "click hover",
    delay: {show: 50, hide: 400}
}), $(document).ready(function () {
    init_sidebar();
    var a = function () {
        $RIGHT_COL.css("min-height", $(window).height());
        var a = $BODY.outerHeight(), b = $BODY.hasClass("footer_fixed") ? -10 : $FOOTER.height(),
            c = $LEFT_COL.eq(1).height() + $SIDEBAR_FOOTER.height(), d = a < c ? c : a;
        d -= $NAV_MENU.height() + b, $RIGHT_COL.css("min-height", d)
    };
    if($(window).width() > 930 && $(window).width() < 1280){
        $SIDEBAR_MENU.find("li.active-sm ul").show();
        $SIDEBAR_MENU.find("li.active ul").hide();
        $SIDEBAR_MENU.find("li.active-sm").addClass("active").removeClass("active-sm");
        $SIDEBAR_MENU.find("li.active").addClass("active-sm").removeClass("active");
        $BODY.toggleClass("nav-md nav-sm");
    }else {
        $SIDEBAR_MENU.find("li.active-sm ul").hide();
        $SIDEBAR_MENU.find("li.active ul").show();
    }
    a();
});
