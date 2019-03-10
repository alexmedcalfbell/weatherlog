$(document).ready(function () {

    let logPath;
    let logPoll;
    let filter;
    let table;

    $('#stats').addClass('active');
    autoUpdateGraph();

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
        autoUpdateGraph();
    });

    function autoUpdateGraph() {
        Plotly.purge($('#graph')[0]);
        let checked = $("#auto-update").is(':checked');
        clearInterval(logPoll);

        // Store or remove the auto update value from local storage
        if (checked) {
            localStorage.setItem("autoUpdate", true);
        } else {
            localStorage.removeItem("autoUpdate");
        }

        //reload the graph
        if (logPath) {
            pollLog();
        }
    }

    // On field change from select box
    $('#graph-field').on('change', function () {
        Plotly.purge($('#graph')[0]);
        readLogAndGraph();
    });

    // Handle click event for a log item row
    // loads Datatables using ajax with the logPath of the clicked log item.
    $(".log-item").click(function () {
        if (logPath !== $(this).closest('tr').attr('id')) {
            logPath = $(this).closest('tr').attr('id');
            readLogAndGraph();
        }
    });

    // Starts an interval that reloads the datatable
    function pollLog() {
        if (logPath) {
            let autoUpdate = $("#auto-update").is(':checked');
            if (autoUpdate) {
                clearInterval(logPoll);
                logPoll = setInterval(function () {
                    console.log('loading log [' + logPath + ']');
                    readLogAndGraph();
                }, interval ? interval * 1000 : 5000);
            }
        }
    }

    function readLogAndGraph() {
        if (logPath) {
            $.ajax({
                url: '/log',
                type: 'POST',
                data: {path: logPath},
                dataType: 'json',
                success: function (logs) {
                    drawGraph(logs, $('#graph-field').val());
                },
                error: function (error) {
                    console.log('Failed to load graph. [ ' + error + ' ]');
                }
            });
        }
    }

    let graphLayout = {
        plot_bgcolor: '#343a40',
        paper_bgcolor: '#343a40',
        margin: {
            t: 0
        },
        xaxis: {
            color: '#ffffff'
        },
        yaxis: {
            color: '#ffffff'
        },
        legendText: {
            color: '#ffffff'
        },
        legend: {
            x: 0,
            y: 1,
            traceorder: 'normal',
            font: {
                family: 'sans-serif',
                size: 11,
                color: '#ffffff'
            },
            bgcolor: '#343a40',
            bordercolor: '#888888',
            borderwidth: 1
        },
        //TODO: Fix hover text position
        // hoverlabel: {
        //     bgcolor: 'lightgrey',
        //     bordercolor: 'darkgrey',
        //     font: {
        //         color: 'blue',
        //         family: 'Open Sans',
        //         size: 14,
        //         padding: 2
        //     }
        // }
    };

    let graphConfig = {responsive: true};

    //TODO: column layout is causing the height to shrink weirdly when shrinking the page
    function drawGraph(logs, field) {

        const [x, y] = getGraphData(logs, field);

        let graphData = [{
            y: y,
            x: x,
            name: '' + getLogName() + '',
            mode: 'lines+markers',
            type: 'scatter',
            showlegend: true
        }];

        Plotly.plot(
            $('#graph')[0],
            graphData,
            graphLayout,
            graphConfig
        );
    }

    function rgbToHex(r, g, b) {
        return "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
    }

    //TODO Improve
    function getGraphData(logs, value) {
        let x = [];
        let y = [];
        logs.forEach((log) => {
            x.push(log.id);

            switch (value) {
                case 'light':
                    y.push(log.light);
                    break;
                case 'rgb':
                    const rgb = log.rgb.split(',');
                    y.push(rgbToHex(Number(rgb[0]), Number(rgb[1]), Number(rgb[2])));
                    break;
                case 'motion':
                    y.push(log.motion);
                    break;
                case 'heading':
                    y.push(log.heading);
                    break;
                case 'temperature':
                    y.push(log.temperature);
                    break;
                case 'pressure':
                    y.push(log.pressure);
                    break;
                default:
                    console.log('No value specified [' + value + ']');
            }


        });
        return [x, y];
    }

    function getLogName() {
        //Get the log name (any thing after the last back or forward slash
        const regex = /[^\\\/]+$/;
        return regex.exec(logPath);
    }


});
