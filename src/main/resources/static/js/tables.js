function sortTable(n) {
    let table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("datatable");
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            /* Check if the two rows should switch place,
            based on the direction, asc or desc: */
            if (dir == "asc") {
                // console.log(x.nodeValue);
                if(x.getAttribute("alt") === null) {
                    if (x.innerText.toLowerCase() > y.innerText.toLowerCase()) {
                        // If so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                }else{
                    if(parseInt(x.getAttribute("alt"), 10) > parseInt(y.getAttribute("alt"), 10)){
                        // If so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                }
            } else if (dir == "desc") {
                if(x.getAttribute("alt") === null) {
                    if (x.innerText.toLowerCase() < y.innerText.toLowerCase()) {
                        // If so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                }else{
                    if(parseInt(x.getAttribute("alt"), 10) < parseInt(y.getAttribute("alt"), 10)){
                        // If so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                }
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            // Each time a switch is done, increase this count by 1:
            switchcount ++;
        } else {
            /* If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again. */
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}

$(document).ready(function() {
    if ($(".table").length > 0) {
        $('.searchbar').keyup(function() {
            searchByColumn($(this).val());
        });

        function searchByColumn(searchVal) {
            let table = $('.table')
            table.find('tr').each(function(index, row) {
                let allDataPerRow = $(row).find('td');
                if (allDataPerRow.length > 0) {
                    let found = false;
                    allDataPerRow.each(function(index, td) {
                        let regExp = new RegExp(searchVal, "i");

                        if (regExp.test($(td).text())) {
                            found = true
                            return false;
                        }
                    });
                    if (found === true) {
                        $(row).show();
                    } else {
                        $(row).hide();
                    }
                }
            });
        }
    }
});