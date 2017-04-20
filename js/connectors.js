
    function change_title(connector_name) {
        $("title").html(connector_name);
        $(".title").html(connector_name);
    }

    function create_apidoc_column(apidoc) {
        return "<td class=\"apidoc\"><a href=\""+ apidoc + "\"><div><i class=\"fa fa-book fa-lg\"></i></div></a></td>";
    }
    
    function create_version_column(version){
        return "<td class=\"version\"><div style=\"\">"+ version + "</div></td>";
    }

    function get_file_name(url) {
        var parser = document.createElement('a');
        parser.href = url;
        var pathname = parser.pathname.split("/");
        long = pathname.length;
        return pathname[long-1];
    }


    function create_samples_column(samples) {
        var samples_li = ""
        $.each(samples, function(i, sample) {
            file_name = get_file_name(sample);
            samples_li += "<li><a href=\"" + sample + "\"><div>" + file_name + "</div></li>";
        });
        return " <td class=\"samples\"><div class=\"dropdown ddmenu\"> Download<ul>" + samples_li + "</ul></div></td>";
    }

    function create_row(row) {
        var version = row["version"];
        var apidoc = row["ApiDoc"];
        var demos = row["demos"];
        var func_doc = row["fun_doc"];
        return create_version_column(version) + create_apidoc_column(apidoc) + create_apidoc_column(func_doc) + create_samples_column(demos);
    }

    function create_table(data) {
            var tbl_body = "";
            var odd_even = false;
            connector_name = data["connector_name"];
            data = data["connector_table"];
            $.each(data, function() {
                var tbl_row = "";
                //$.each(this, function(k , v) {
                //    tbl_row += "<td>"+v+"</td>";
                //});
                tbl_row = create_row(this);
                tbl_body += "<tr class=\""+( odd_even ? "odd" : "even")+"\">"+tbl_row+"</tr>";
                odd_even = !odd_even;
            });
            $("#connectors-table tbody").append(tbl_body);
	
        }
    function links_behav() {
        $(".dropdown").on("click", function(e){
            e.preventDefault();

              if($(this).hasClass("open")) {
                $(this).removeClass("open");
                $(this).children("ul").slideUp("fast");
              } else {
                var list = document.getElementsByClassName("dropdown");;

                for (var i=0, item; item = list[i]; i++) {
                      if($(item).hasClass("open")) {
                        $(item).removeClass("open");
                        $(item).children("ul").slideUp("fast");
                      }
                }

                $(this).addClass("open");
                $(this).children("ul").slideDown("fast");
              }
    });

        $("a").on("click", function(e){
              e.preventDefault();
                var demoLink = this.getAttribute("href");
                window.location.href = demoLink;
        });
    }

    function get_relative_url(url) {
        var project = "connectors-commons";
        return "/" + project + url;
    }
$(document).ready(function()
{
    $.get( get_relative_url("/head.html") , function(data) {
        $( "head" ).html(data);
    });
    $.get( get_relative_url("/conn_table.html"), function( data ) {
        $( "body" ).html( data );
        $.getJSON("data.json", function(data) {
                        change_title(data["connector_name"]);
                        create_table(data);
                        links_behav();
                }
        );
    });
});
