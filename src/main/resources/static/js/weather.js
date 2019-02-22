$(document).ready(function () {

    // Handle click event for a log item row
    // loads Datatables using ajax with the logPath of the clicked log item.
    $(".log-item").click(function () {
        var logPath = $(this).closest('tr').attr('id');

        $('#log-table').DataTable({
            destroy: true,
            processing: true,
            serverSide: false,
            lengthMenu: [[20, 50, 100, -1], [20, 50, 100, "All"]],
            ajax: {
                type: 'POST',
                url: '/log',
                data: {'path': logPath},
                dataSrc: ''
            },
            columns: [
                {data: 'light', defaultContent: '-'},
                {data: 'rgb', defaultContent: '-'},
                {data: 'motion', defaultContent: '-'},
                {data: 'heading', defaultContent: '-'},
                {data: 'temperature', defaultContent: '-'},
                {data: 'pressure', defaultContent: '-'}
            ]
        });
    });

    $(".save").click(function () {
        var logPath = $('#log-path').val();
        storeLogPath(logPath);
    });

    function storeLogPath(logPath) {
        if (typeof (Storage) !== "undefined") {
            // Store
            localStorage.setItem("logPath", logPath);

            $('.alert').addClass('show');
        }
    }

    $('#log-path').val(localStorage.getItem("logPath"));

});