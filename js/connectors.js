
    var MULESOFT_GITHUBIO_URL = "https://mulesoft.github.io'";
    var APIDOC_DEFAULT_FOLDER = "apidocs";
    var DEMOS_DEFAULT_FOLDER = "demo";
    var FUNCT_DOC_DEFAULT_FOLDER = "functional";
    var USER_MANUAL_DEFAULT_DOC = "user-manual.html";

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

    function get_githubio_io_version_link( version_id, connector_repo) {
        return MULESOFT_GITHUBIO_URL + "/" + connector_repo + "/version_id" ;

    }

    function get_api_doc_link(api_doc_node, version_id, connector_repo) {
        var api_doc_url = get_githubio_io_version_link(version_id, connector_repo)+ "/" + APIDOC_DEFAULT_FOLDER;
        var api_doc_file_name = "";
        if ( "url" in api_doc_node && api_doc_node["url"]) {
            api_doc_url = api_doc_node["url"];
        }

        if (api_doc_node["name"]) {
            api_doc_file_name = api_doc_node["name"];
        }

        return api_doc_url + "/" + api_doc_file_name;
    }

    function get_demos_links(demos_node, version_id, connector_repo) {
        var demos_url = "";
        var demos_links = [];
        $.each(demos_node, function(){
            demos_url = get_githubio_io_version_link(version_id, connector_repo) + "/" + DEMOS_DEFAULT_FOLDER;
            if ( "url" in this && this["url"]) {
                demos_url = this["url"];
            }
            demos_links.append( demos_url + "/" + this["name"]);
        });
        return demos_links;
    }

    function get_functional_doc_links(function_doc_node, version_id, connector_repo) {
        var func_doc_url = get_githubio_io_version_link(version_id, connector_repo) + "/" + FUNCT_DOC_DEFAULT_FOLDER;
        var user_manual_doc = USER_MANUAL_DEFAULT_DOC;

        if (function_doc_node) {
            if ("url" in function_doc_node && function_doc_node["url"]){
                func_doc_url = function_doc_node["url"];
            }

            if ("name" in function_doc_node && function_doc_node["name"]) {
                user_manual_doc = function_doc_node["name"];
            }

        } 
        return func_doc_url + "/" + FUNCT_DOC_DEFAULT_FOLDER + "/" + user_manual_doc;

    }

    function create_row(row, connector_repo) {
        var version = row["id"];
        var apidoc = get_api_doc_link(row["api_doc"], version, connector_repo);
        var demos = get_demos_links(row["demos"], version, connector_repo);
        var func_doc = get_functional_doc_links(row["fun_doc"], version, connector_repo);

        return create_version_column(version) + create_apidoc_column(apidoc) + create_apidoc_column(func_doc) + create_samples_column(demos);
    }

    function create_table(data) {
            var tbl_body = "";
            var odd_even = false;
            connector_name = data["name"];
            repo_name = data["repoName"]
            data = data["versions"];
            $.each(data, function() {
                var tbl_row = "";
                //$.each(this, function(k , v) {
                //    tbl_row += "<td>"+v+"</td>";
                //});
                tbl_row = create_row(this, repo_name);
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
                        change_title(data["name"]);
                        create_table(data);
                        links_behav();
                }
        );
    });
});
