var posts = {};
function loadPosts() {
    var postsDiv = document.getElementById("posts");
    //{items: [
    //  {id: "", title: "", author: "", text: ""},
    //  {id: "", title: "", author: "", text: ""},
    // ]}
    $.getJSON(
        "/posts?count=5",
        function (data) {
            posts = data;
            // console.log(data);
            for (var i = 0; i < data.length; i++) {
                postsDiv.innerHTML += "<div class='card' style='width: 20rem; float: left; margin: 5px'>\
                    <div class='card-block'>\
                        <h4 class='card-title'>" + data[i].title + "</h4>\
                        <span class='badge badge-default badge-pill badge-info'>" + data[i].author + "</span>\
                        <p class='card-text'>\
                            " + data[i].text.replace("\n","<br/>").slice(0,100) + "\
                            <span class='collapse' id='viewdetails" + data[i].id + "'>"+data[i].text.replace("\n","<br/>").slice(100)+"</span>\
                            <a class='btn showdetails' data-toggle='collapse' data-target='#viewdetails"+data[i].id+"'></a>\
                            <a href='#' onclick='return showNewsPreview(" + data[i].id + ")' class='btn btn-primary btn-sm'>Create news post</a>\
                        </p>\
                    </div>\
                </div>";
            }
        }
    );
}

function showNewsPreview(id) {
    BootstrapDialog.show({
        message: 'Hi Apple!'
    });
    return false;
}