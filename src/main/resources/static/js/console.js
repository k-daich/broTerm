console.log('[setConsoleId.js addConsole()] start' + $("#init-0"));

// idを初期値からユニークなＳＥＱに変更する
$("#init-0").attr("id", "console-id-" + ++consoleIdSeq);
console.log('[setConsoleId.js addConsole()] end' + $("#init-0"));
