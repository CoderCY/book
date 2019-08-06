package cn.cy.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by 30721 on 2019/8/2.
 */
public class ScriptUtil {

    public static Object getJavaScript(String cp) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            engine.eval("var " + cp + "\n" +
                    "function base64decode(str) {\n" +
                    "    var base64EncodeChars = \"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/\";\n" +
                    "    var base64DecodeChars = new Array( - 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);\n" +
                    "    var c1, c2, c3, c4;\n" +
                    "    var i, len, out;\n" +
                    "    len = str.length;\n" +
                    "    i = 0;\n" +
                    "    out = \"\";\n" +
                    "    while (i < len) {\n" +
                    "        do {\n" +
                    "            c1 = base64DecodeChars[str.charCodeAt(i++) & 255]\n" +
                    "        } while ( i < len && c1 == - 1 );\n" +
                    "        if (c1 == -1) {\n" +
                    "            break\n" +
                    "        }\n" +
                    "        do {\n" +
                    "            c2 = base64DecodeChars[str.charCodeAt(i++) & 255]\n" +
                    "        } while ( i < len && c2 == - 1 );\n" +
                    "        if (c2 == -1) {\n" +
                    "            break\n" +
                    "        }\n" +
                    "        out += String.fromCharCode((c1 << 2) | ((c2 & 48) >> 4));\n" +
                    "        do {\n" +
                    "            c3 = str.charCodeAt(i++) & 255;\n" +
                    "            if (c3 == 61) {\n" +
                    "                return out\n" +
                    "            }\n" +
                    "            c3 = base64DecodeChars[c3]\n" +
                    "        } while ( i < len && c3 == - 1 );\n" +
                    "        if (c3 == -1) {\n" +
                    "            break\n" +
                    "        }\n" +
                    "        out += String.fromCharCode(((c2 & 15) << 4) | ((c3 & 60) >> 2));\n" +
                    "        do {\n" +
                    "            c4 = str.charCodeAt(i++) & 255;\n" +
                    "            if (c4 == 61) {\n" +
                    "                return out\n" +
                    "            }\n" +
                    "            c4 = base64DecodeChars[c4]\n" +
                    "        } while ( i < len && c4 == - 1 );\n" +
                    "        if (c4 == -1) {\n" +
                    "            break\n" +
                    "        }\n" +
                    "        out += String.fromCharCode(((c3 & 3) << 6) | c4)\n" +
                    "    }\n" +
                    "    return out\n" +
                    "};\n" +
                    "\n" +
                    "function getImg() {\n" +
                    "  return eval(eval(base64decode(cp).slice(4)));\n" +
                    "}");
            Invocable invocable = (Invocable) engine;
            return invocable.invokeFunction("getImg");
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
