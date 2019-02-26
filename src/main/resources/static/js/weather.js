$(document).ready(function () {

    // Handle click event for a log item row
    // loads Datatables using ajax with the logPath of the clicked log item.
    $(".log-item").click(function () {
        var logPath = $(this).closest('tr').attr('id');
        loadLogTable(logPath);

        pollLog(logPath);
    });

    function pollLog(logPath) {
        var logPoll = setInterval(function () {

            console.log('loading log');
            loadLogTable('C:\\Users\\alexanderbell\\Desktop\\weather\\enviroLONDONtoPETERBOROUGH.log')
        }, 3000);
    }

    function loadLogTable(logPath) {
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
    }

    // setTimeout(explode, 2000);
});