$(document).ready(function () {

    let logPath;
    let logPoll;
    let filter;
    let table;

    $('#logs').addClass('active');

    //TODO: store lengthMenu val / page number

    function rgbToHex(r, g, b) {
        return "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
    }

    // Read / set the auto update checkbox if present
    if (localStorage.getItem("autoUpdate")) {
        $("#auto-update").prop('checked', true);
    }

    // Read / set the interval field if present
    let interval = localStorage.getItem("interval");
    if (interval) {
        $("#update-interval").val(interval);
    }

    $("#update-interval").on('input', function () {
        interval = $("#update-interval").val();
        if (interval.length) {
            localStorage.setItem("interval", interval);
        } else {
            localStorage.removeItem("interval");
        }
        //reload the Datatable
        if (logPath) {
            pollLog();
        }
    });

    // Makes the datatable reload the current log at the specified interval
    $("#auto-update").click(function () {
        let checked = $("#auto-update").is(':checked');
        clearInterval(logPoll);

        // Store or remove the auto update value from local storage
        if (checked) {
            localStorage.setItem("autoUpdate", true);
        } else {
            localStorage.removeItem("autoUpdate");
        }

        //reload the Datatable
        if (logPath) {
            pollLog();
        }
    });

    // Handle click event for a log item row
    // loads Datatables using ajax with the logPath of the clicked log item.
    $(".log-item").click(function () {
        logPath = $(this).closest('tr').attr('id');
        loadLogTable();
        pollLog();
    });

    // Starts an interval that reloads the datatable
    function pollLog() {
        let autoUpdate = $("#auto-update").is(':checked');
        if (autoUpdate) {
            clearInterval(logPoll);
            logPoll = setInterval(function () {
                console.log('loading log [' + logPath + ']');
                loadLogTable(logPath);
            }, interval ? interval * 1000 : 5000);

        }
    }

    // Initialise the Datatable for the provided log path.
    function loadLogTable() {

        table = $('#log-table').DataTable({
            destroy: true,
            processing: false,
            serverSide: false,
            lengthMenu: [[20, 50, 100, -1], [20, 50, 100, "All"]],
            oSearch: {
                sSearch: filter
            },
            ajax: {
                type: 'POST',
                url: '/log',
                data: {'path': logPath},
                dataSrc: ''
            },
            columns: [
                // {
                //     visible: false,
                //     targets: [0],
                //     render: function (data, type, row, meta) {
                //         return meta.row;
                //     }
                // },
                {data: 'id', defaultContent: '-', visible: false},
                {data: 'light', defaultContent: '-'},
                {
                    data: 'rgb', defaultContent: '-',
                    createdCell: function (td, data) {
                        const rgb = data.split(',');
                        $(td).css('background-color', rgbToHex(Number(rgb[0]), Number(rgb[1]), Number(rgb[2])));
                    }
                },
                {data: 'motion', defaultContent: '-'},
                {data: 'heading', defaultContent: '-'},
                {
                    data: 'temperature', defaultContent: '-',
                    createdCell: function (td, data) {
                        $(td).css('background-color', temperatureToHex(data));
                        $(td).css('color', '#333333');
                    }
                },
                {data: 'pressure', defaultContent: '-'}
            ]
        });

        // Set the current value of filter so it's preserved when and if the table reloads.
        table.on('search.dt', function () {
            filter = $('.dataTables_filter input')[0].value;
        });

        $("#log-table").css("width", "100%");
    }

    //Celsius
    function temperatureToHex(temperature) {
        if (temperature < 5) {
            return '#ffffff';
        } else if (temperature < 10 && temperature >= 5) {
            return '#00ffff';
        } else if (temperature < 15 && temperature >= 10) {
            return '#66ccff';
        } else if (temperature < 20 && temperature >= 15) {
            return '#ffff66';
        } else if (temperature < 25 && temperature >= 20) {
            return '#ffcc00';
        } else if (temperature < 30 && temperature >= 25) {
            return '#ff9900';
        } else if (temperature > 30) {
            return '#ff3300';
        } else {
            console.log('Unable to determine color [' + temperature + ']');
            return '#343a40';
        }
    }


});
