$(document).ready(function () {
    $(document).on("click", "#languageDropdownMenuButton a", function (e) {
        e.preventDefault();
        e.stopPropagation();
        let languageSelectedValue = $(this).data("value");
        let url = window.location.pathname + '?lang=' + languageSelectedValue;
        window.location.assign(url);
    });
});