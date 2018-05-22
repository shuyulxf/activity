package indi.shuyu.core.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;

public class ChooseInteraction {

	/*
	 * 求公共子串，采用动态规划算法。
	 * 其不要求所求得的字符在所给的字符串中是连续的。     cCC
	 *
	 * */
	public static String longestCommonSubstring(String str1, String str2) {
		int sublen1 = str1.length();
		int sublen2 = str2.length();

		//二位数组描述矩阵
		int[][] c = new int[sublen1 + 1][sublen2 + 1];
		// 动态规划计算所有子问题
		for (int i = sublen1 - 1; i >= 0; i--) {
			for (int j = sublen2 - 1; j >= 0; j--) {
				if (str1.charAt(i) == str2.charAt(j))
					c[i][j] = c[i + 1][j + 1] + 1;
				else
					c[i][j] = Math.max(c[i + 1][j], c[i][j + 1]);
			}
		}
		String result="";
		int i = 0, j = 0;
		while (i < sublen1 && j < sublen2) {
			if (str1.charAt(i) == str2.charAt(j)) {
				result= result+str1.charAt(i);
				i++;
				j++;
			} else if (c[i + 1][j] >= c[i][j + 1])
				i++;
			else
				j++;
		}
		return result;
	}

	/**
	 * 计算Jaccard相似度  cc
	 *
	 * @param jointLength
	 * @param len1
	 * @param len2
	 * @return
	 */
	public static double computerJaccardSimilary(int jointLength,int len1, int len2){
		if(jointLength<0 ||len1<0 || len2<0 || (len1+len2)<=0){
			return 0;
		}
		double sim=(double)jointLength/(double)(len1+len2-jointLength);
		return sim;
	}

	/**
	 * CC  判断两个询问和交互选项是否是相似的
	 * @param query
	 * @param item
	 * @return
	 */
	public static boolean isSimalarityQueryAndChooseItem(String query, String item){
		if(query==null || item==null || query.length()==0 || item.length()==0){
			return false;
		}

		//求取最大公共子串
		String commonStr=longestCommonSubstring(query,item);

		if(commonStr.length()<=1){//最大公共子串长度小于等于1，则认为不相似
			return false;
		}
		//最大公共子串等于query或者item 那么相似  和item相等是因为可能query里面有识别出来多余的字
		if(commonStr.equals(query)||commonStr.equals(item)){
			return true;
		}
		//计算相似度
		double jaccardSim=computerJaccardSimilary(commonStr.length(),query.length(),item.length());

		//query长度小于等于item       最大公共子串的长度不小于二 && Jaccard系数大于阈值  那么相似
		if(query.length()<=item.length()&& jaccardSim>0.3){
			return true;
		}
		//query长度大于item（可能query有错别字或者多余的字）&& query的长度-item的长度小于等于3（询问长度大于item3个字数就直接不认为相似）    （item长度-最大公共子串长度）小于等于2 && Jaccard系数大于阈值
		if(query.length()>item.length()&& (query.length()-item.length())<=3 && (item.length()-commonStr.length())<=2 && jaccardSim>0.3){
			return true;
		}

		return false;
	}

	/**
	 * 计算交互选项和query的相似度，找到最相似的返回交互选项序号
	 * @param query
	 * @param his
	 * @return
	 */
	public static String compareChooseItemAndQuery(String query, JSONArray options) {

		ArrayList<String> outList = new ArrayList<String>();
		
		for (int i = 0; i < options.size(); i++) {
			
			String option = options.getString(i);
			if(isSimalarityQueryAndChooseItem(query, option)){
				return (i+1) + "";
			}
		}
		
		return "-1";
	}

	private static HashMap<String, String> setIntegerMap() {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("一", "1");
		map.put("二", "2");
		map.put("三", "3");
		map.put("四", "4");
		map.put("五", "5");
		map.put("六", "6");
		map.put("七", "7");
		map.put("八", "8");
		map.put("九", "9");
		
		return map;
		
	}
	
	public static boolean judgeAnswerIsRight(String query, JSONArray options, String answer) {
		
		String opt = ChooseInteraction.compareChooseItemAndQuery(query, options);
		boolean rlt = false;
		
		if ("-1".equals(opt)) {
			
			if ("123456789".indexOf(query) != -1) {
				if (query.equals(answer)) rlt = true;
			} else {
				HashMap<String, String> map = setIntegerMap();
				for (Entry<String, String> entry : map.entrySet()) {  
					String key = entry.getKey(),
						   value = entry.getValue();
					if (query.indexOf(key) != -1 && value.equals(answer)) {
						rlt = true;
					}
				}  
			}			
		} else {
			if (answer.equals(opt)) rlt = true;
		}
		
		return rlt;
	}
	public static void main(String[] args) {
		// 设置字符串长度
		int sublen1 = 5;
		int sublen2 = 6;
		// 随机生成字符串
//        String str2 = "((ad)";
//        String str1 = "(a((d)";
		String str1 = "副爱说明";
		String str2= "乐享4g副卡说明";

//		String result=longestCommonSubstring(str1,str2);
//		System.out.println(result);
		
		JSONArray options = new JSONArray();
		options.add("很好");
		options.add("不好");
		String query = "我很好";
		String rlt = compareChooseItemAndQuery( query, options);
		
		boolean r = judgeAnswerIsRight(query, options, "1");
		System.out.println("rlt:"+r);
		//二位数组描述矩阵
//        int[][] c = new int[sublen1 + 1][sublen2 + 1];
//        // 动态规划计算所有子问题
//        for (int i = sublen1 - 1; i >= 0; i--) {  
//            for (int j = sublen2 - 1; j >= 0; j--) {  
//                if (str1.charAt(i) == str2.charAt(j))  
//                    c[i][j] = c[i + 1][j + 1] + 1;  
//                else  
//                    c[i][j] = Math.max(c[i + 1][j], c[i][j + 1]);  
//            }  
//        }  
//        System.out.println("str1:" + str1);  
//        System.out.println("str2:" + str2);  
//        System.out.print("LCS:");  
//        int i = 0, j = 0;  
//        while (i < sublen1 && j < sublen2) {  
//            if (str1.charAt(i) == str2.charAt(j)) {  
//                System.out.print(str1.charAt(i));  
//                i++;  
//                j++;  
//            } else if (c[i + 1][j] >= c[i][j + 1])  
//                i++;  
//            else  
//                j++;  
//        }  
	}
}