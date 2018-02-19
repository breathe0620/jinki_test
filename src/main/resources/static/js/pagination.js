function goToPage(pageNo) {
    $("input[name=page]").val(pageNo);
    searchFilter();
    $("#paginationForm").submit();
}
