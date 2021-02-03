console.log('[setConsoleId.js addConsole()] start';

// idを初期値からユニークなＳＥＱに変更する
$("#init-0").attr("id", "console-id-" + ++consoleIdSeq);
console.log('[setConsoleId.js addConsole()] end');
