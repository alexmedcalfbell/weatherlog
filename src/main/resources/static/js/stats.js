$(document).ready(function () {

    let logPath;
    let logPoll;
    let filter;
    let table;

    $('#stats').addClass('active');

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
        readLog();
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


    function readLog() {
        $.ajax({
            url: '/log',
            type: 'POST',
            data: {path: logPath},
            dataType: 'json',
            success: function (logs) {
                let x = [];
                let y = [];
                logs.forEach((log) => {
                    x.push(log.id);
                    y.push(log.light);
                });

                drawGraph(x, y);
                // drawGraph2(x, y);
            },
            error: function (error) {
                console.log('Failed to load graph. [ ' + error + ' ]');
            }
        });
    }

    //TODO: column layout is causing the height to shrink weirdly when shrinking the page
    function drawGraph(x, y) {
        var layout = {
            plot_bgcolor: '#343a40',
            paper_bgcolor: '#343a40',
            margin: {
                t: 0
            },
             height: 600,
            xaxis: {
                color: '#fff'
            },
            yaxis: {
                color: '#fff'
            },
            legendText: {
              color: '#fff'
            },
        };
        let config = { responsive: true };

        let lightGraph = document.getElementById('light-graph');
        Plotly.plot(
            lightGraph,
            [{ y: y, x: x }],
            layout,
            config
        );
    }

    function drawGraph2(x,y){
        var ctx = document.getElementById("myChart").getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: x,
                datasets: [{
                    label: '# of Votes',
                    data: y,
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                },
                plugins: {
                    colorschemes: {
                        scheme: 'brewer.Dark2-3'
                    }
                }
            }
        });
    }


});
