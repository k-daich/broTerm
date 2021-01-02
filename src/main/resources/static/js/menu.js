// DOM読み込み時実行処理
$(document).ready(function () {
    console.log('[menu.js onload] start');
    // サーバ情報を他jsから読み込む
    loadScript("js/data/servers.js", function () {
        // selectボックスの選択肢の作成を行う
        addSelectOptions();
    });
    // ログインボタンへのイベントリスナーの追加
    addLoginButtonEventListener();
    console.log('[menu.js onload] end');
});

/**
 * ログインボタン押下時イベントを登録する
 */
function addLoginButtonEventListener() {
    $('#loginTextFormButton').on('click', function () {
        console.log('[menu.js ajax] start');
        // 通信実行
        $.ajax({
            type: "post",
            url: "http://localhost:8080/loginFromTextForm",
            // フォームデータを直列化させて設定する
            data: $("#loginFromTextForm").serialize(),
            contentType: 'application/json',
            dataType: "json",
            success: function (json_data) {
                // 成功時の処理
                console.log('[menu.js ajax] success');
            }
        });
        console.log('[menu.js ajax] end');
    });
}

/**
 * selectボックスの選択肢を作成する
 */
function addSelectOptions() {
    console.log('[menu.js addSelectOptions] start');
    for (let _hostName in SERVERS) {
        $('#server-btn-area').append('<div>' + _hostName + '(' + SERVERS[_hostName].IP + ')</div><br/>');
        // $('#login-select').append('<option value="' + _serverName + '">' + SERVERS[_serverName].hostname + '@' + SERVERS[_serverName].user + '</option>');
        SERVERS[_hostName].users.forEach(function (elem, index) {
            $('#server-btn-area').append(
                '<a class="btn solid-btn">\
                <span class="solid-btn-front">' + elem + '</span>\
                <i class="fas fa-angle-right fa-position-right"></i>\
            </a>');
        });
    }
    console.log('[menu.js addSelectOptions] end');
}

/**
 * srcの読み込みを待ってからcallbackを実行する
 */
function loadScript(src, callback) {
    console.log('[menu.js loadScript] start. src -> ' + src);
    var done = false;
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.src = src;
    head.appendChild(script);
    // Attach handlers for all browsers
    script.onload = script.onreadystatechange = function () {
        if (!done && (!this.readyState ||
            this.readyState === "loaded" || this.readyState === "complete")) {
            done = true;
            callback();
            // Handle memory leak in IE
            script.onload = script.onreadystatechange = null;
            if (head && script.parentNode) {
                head.removeChild(script);
            }
        }
    };
    console.log('[menu.js loadScript] end. src -> ' + src);
}
