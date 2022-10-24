<html>
<body>

<div align="center">
    <#if !error?has_content>
        <div style="color: black">Let's play Tic Tac Toe game!</div>
        <br>

        <#if result?has_content>
            <div>
                <div>${result}</div>
                <#if isWin>
                    <a class="navbar-brand" href="/reset">Let's play again</a>
                </#if>
            </div>
        </#if>

        <#if turn == "X" && !isWin>
            <div>
                <div>Enter position of X</div>
                <form action="/send" method="post">
                    <input type="text" name="position"/>
                    <button type="submit">Send</button>
                </form>
            </div>
        </#if>

        <#if turn == "O" && !isWin>
            <div>
                <div>Enter position of O</div>
                <form action="/send" method="post">
                    <input type="text" name="position"/>
                    <button type="submit">Send</button>
                </form>
            </div>
        </#if>

        <div style="margin-top:50px;">
            <#list boards as board>
                <tr>
                    <td>${dash} <br> ${board} <br></td>
                </tr>
            </#list>
        </div>

        <div align="center" style="margin-top:25px;">
            <div>
                <a class="navbar-brand" href="/reset">reset</a>
            </div>
        </div>
    </#if>

    <#if error?has_content>
        <div>
            <div>${error}</div>
            <a class="navbar-brand" href="/">Enter the valid position, mon ami</a>
        </div>
    </#if>

</div>

</body>
</html>
