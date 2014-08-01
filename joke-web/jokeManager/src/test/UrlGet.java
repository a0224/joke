package test;

import org.springframework.web.context.request.WebRequest;

public class UrlGet {

	public String GetHtml(String url) {
		String sExp = null;
		String sRslt = null;
		WebResponse oWebRps = null;
		WebRequest oWebRqst = WebRequest.Create(url);
		oWebRqst.Timeout = 50000;
		try {
			oWebRps = oWebRqst.GetResponse();
		} catch (WebException e) {
			sExp = e.Message.ToString();
			Response.Write(sExp);
		} catch (Exception e) {
			sExp = e.ToString();
			Response.Write(sExp);
		} finally {
			if (oWebRps != null) {
				StreamReader sr = new StreamReader(oWebRps.GetResponseStream(),
						Encoding.GetEncoding("GB2312"));
				sRslt = sr.ReadToEnd();
				sr.Close();
				oWebRps.Close();
			}
		}
		return sRslt;
	}

	public String[] FilterHtml(string html) {
		String[] rS = new String[2];
		String s = html;
		s = Regex.Replace(s, "\\s{3,}", "");
		s = s.Replace("\r", "");
		s = s.Replace("\n", "");
		string Pat = "<td align=\"center\" class=\"24p\"><B>(.*)</B></td></tr><tr>.*(<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\">.*</table>)<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">(.*)<td align=center class=l6h>";
		Regex Re = new Regex(Pat);
		Match Ma = Re.Match(s);
		if (Ma.Success) {
			rS[0] = Ma.Groups[1].ToString();
			rS[1] = Ma.Groups[2].ToString();
			pgStr = Ma.Groups[3].ToString();
		}
		return rS;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
