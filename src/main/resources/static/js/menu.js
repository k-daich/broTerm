var consoleIdSeq = 0;

// DOM読み込み時実行処理
$(document).ready(function () {
    console.log('[menu.js onload] start');
    // サーバ情報を他jsから読み込む
    loadScript("js/data/servers.js", function () {
        // selectボックスの選択肢の作成を行う
        addSelectOptions();
    });
    // ログインボタンによるAjax通信のイベントリスナー追加
    addLoginButtonEventListener();
    addButtonValidEvent();
    console.log('[menu.js onload] end');
});

/**
 * ボタンの活性化/非活性化イベントを登録する
 */
function addButtonValidEvent() {
    $(".textFormInput").each(function (index, obj) {
        console.log('[textFormInput each object] : ', obj);

        /**
         * ボタンの活性化/非活性化を行う
         */
        obj.addEventListener("change", function () {
            // フォーム項目に空値が存在するかフラグ
            var emptyFlg = false;

            $(".textFormInput").each(function (index, obj) {
                if (obj.value == '') {
                    // 空項目があればフラグを立てて繰り返し処理を終了させる
                    // console.log('#' + obj.id + ' is [empty]');
                    emptyFlg = true;
                    return false;
                }
                else {
                    // console.log('#' + obj.id + ' is [not empty]');
                }
            });
            // 空項目があればボタンを非活性化する
            $("#loginTextFormButton").attr("disabled", emptyFlg);
        });
    });
}

/**
 * 
 */
function addConsole(sessionId) {
    // コンソール表示エリアHTMLの読み込み
    $("#console-area").load("/html_parts/console.html");
    console.log("console-init" , $("#console-init"));
    $("#console-init").attr("consoleNum" , sessionId);
}

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
            // フォーム情報をJSON形式に変換してPOST情報に設定する
            data: JSON.stringify(createFromJsonData($('#loginTextForm'))),
            contentType: 'application/json',
            dataType: "json",
            cache: false,
            async: true
        })
            .done(function (data) {
                // 成功時の処理
                // ログ出力
                alert('[menu.js ajax] success');

                // ログインフォームをフェードアウト
                $('#login-forms').addClass("fadeout");
                // 成功時の処理
                // ログ出力
                console.log('[menu.js ajax] success');
                console.log('[menu.js ajax data] ', data);

                // コンソール表示エリアHTMLの読み込み
                addConsole(data.sessionId);
            })
            .fail(function () {
                // 通信が失敗したときの処理
                // ログ出力
                alert('[menu.js ajax] fail');
            });
        // .always(function () {
        //     // 通信が完了したときの処理
        //     // ログ出力
        //     alert('[menu.js ajax] always');
        // });
    });
}

/**
 * フォームデータをJSON形式に変換する
 * @param {*} formId formタグのid
 */
function createFromJsonData(form) {
    var formdata = form.serializeArray();
    var json_data = {};
    $(formdata).each(function (index, obj) {
        json_data[obj.name] = obj.value;
    });
    // console.log('$(formId) : ', form);
    // console.log('formdata : ', formdata);
    // console.log('json_data : ', json_data);
    return json_data;
}

// ビジーwaitを使う方法
function sleep(waitMsec) {
    var startMsec = new Date();

    // 指定ミリ秒間だけループさせる（CPUは常にビジー状態）
    while (new Date() - startMsec < waitMsec);
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
