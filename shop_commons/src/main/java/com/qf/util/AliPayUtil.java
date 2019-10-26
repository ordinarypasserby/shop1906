package com.qf.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * @author DingYuHui
 * @Date 2019/10/26
 */
public class AliPayUtil {
    private static AlipayClient alipayClient = null;
    static {
       alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016101300677556",
                "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC8G7/upML1PO6qPlo6CC1J/jh2YVuMteTK0UE3h+pw/y2R1Z+CkQffn1lEzs0AueuUa7s1LxOlumx3pVeoq6HhZvo+wrNFnIQNwH6VLT1nbOqjqKTJv2WRSCW8i7HNamdkz8576m52fgwJoazC8EU2IsY5BfPTRoWVMyGUJn+8fw1XaNzdaB9XC2/jY16z/EWmzcCU942lsKY6KribH9ZG2L3hCrlTsYJCowvyuZlDDL1gBf1PDhSPkhb84363/pi4K/NP7R79TEQaMfSqrmtVcsVjuUeogV/ERi/E5w5rbh3/D1MrzRuTttrJaVv4fmKN3MvLxMWDWbS7cPFu4trdAgMBAAECggEAJNe6i98xYB8DD80OlvcrB6cHIAiJidHGcgLyJxIz1NXl8v5IM6Z8SExJi7SFanL/tR40mIIT8w3EWNrFhH+5QRCOSuSPgD7bjmX5ZPZrZGpsuok5XVpjZkUfPwKNXJJOlewtoQ+VexfM+8XytGui4quE734+906Es2hEV2QC+MQDGqdvhSwP8naxYG4lm4LQJyejpxX3GpD0HMavIQV8MwqVKDORNFvPdNFhVar7LliUwjufUszXsd/fuMjhye9v1ObDQttRm26jKIxknQrxQiKlAJ78jrJMspj7TCeZHtIyt3UZj1A9ZXT8XSFL8u18FdLufGDOw8s1gwxWuG21gQKBgQD37w9EY3+TFHF3hcHrLi/e78hrKmDh1+Tcn0IkPbBKaTQqkbCbOnSlxIeej5k8R5O4/nGyA1gKlndwvhjQl9O2/S8bopmaTtcBlPbqIJZylKQM93SzgkVOQ5wQG5MNQsPb0Lpya4fET8Z6IxjBgXk3n6g9vMBJKg73Pw0gGnHFpQKBgQDCOm2n3Fwe1l9/cYEZ+zhDu+QruBkj73XQ1IfQSEhuk9/srtQ53Kr0koNU/q6+65okUKjD8q2c3ZcRSoG2w9e4Zl+uZpf03vTt0KCm82gFQaWnNL6IOq4Ub7f3gvpkHLZajy7N/tvdA+lSKwdEnzLRrON/J4XRslyp5VZ1u2xq2QKBgFtpTaS2VpwAxpD9xPCT99xnZep1FoFOGzhok3dPM0dMIIqtaoLg+47+tc31PdcmwMVNMtgiOXy121W7EOUfhVSLZqn2ulFVThpsNNk/qJiYSx/owKNKnYjsjoa10/LosdPC0mtLUCRGEXSIqiD2Pp4OhHY2uGKBWkE4gvtaJKZtAoGABegS95za7FEmnFCOVYTvRBgNq0qw8QfSNGSSMMJR+ZLjp2X1rEDKrh9agRYxqDqv+FS/nR3fK7sNYgg7A5zXDZOHa11RV7AEEMtcZ1dVnXtOl1QiSzBFpPd0x8Ei7jJ1VTGvgbIza3g3+zi1wt94gynXzUEdb7AV41DHHVNyy5kCgYBTaPjnU2G8w1tQ2LR62rUseO+eiPJBrpAEkaql5Nf42iTkEAeMVwsPg2kmjrEUb96zQGO3uA7vdu7sll7JLfw5WXtYXweOwoJklc/U3bvnLCuyQJQcleK6VykM12VdMTdYWLVR/IAGMs2m4Bx6FP5nlYtwPo0Y54fSjjeU1yEURg==",
                "json",
                "utf-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlLpAresGnNFqplpH1v4EL6mzVdXdjxNup+cFpnodQ0cTTAhU/T5MwOqjLft5EX63lszcpbRpp9CZOfoWAqtYFkB+vwXnzWACnbg908Xxbw+kF17H7JcZ+kDQF9k22kLQkCE1g8gVAePWGtVkpxg5EDlRGwSHSdTfhSBQxpglvEJmZeDFQJLfAaZ4cQkl52H6fozxXUvYT6rvtMllSDVthzus54iHp1YR31uRGUKhkLBqVChpNTBpKRb5sT+OcvBeoOcxnY1vLQ55wLxhQHN9yVnLCPd5TVyKmpVmD1/8HZgfUIsIt5ooPSdJeGH/WSd6hY89DEzuSynXZFUojuaZbwIDAQAB",
                "RSA2");
    }
    //初始话alipay核心对象
    public static AlipayClient getAlipayClient(){
        return alipayClient;
    }
}
