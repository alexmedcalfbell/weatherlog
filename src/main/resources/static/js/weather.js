$(document).ready(function () {

    $(".log-item").click(function (event) {
        var logPath = $(this).closest('tr').attr('id');
        $.ajax({
            url: '/log',
            type: 'POST',
            data: {"path": logPath},
            dataType: 'json',
            success: function (log) {
                $('.log-data').text(log.data);
            },
            error: function (error) {

            }
        });

    });

});
