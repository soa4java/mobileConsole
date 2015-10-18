package com.yuchengtech.mobile.console.common;

import java.util.HashMap;
import java.util.Map;

public class ModuleInfo {
	
	public static final String Dict = "Dict";
	public static final String DictItem = "DictItem";
	public static final String CrawlRecord = "CrawlRecord";
	public static final String LoginLog = "LoginLog";
	
	public static final String BjInformation = "BjInformation";	
	public static final String CreditRates = "CreditRates";
	public static final String DepositRates = "DepositRates";
	public static final String EconomicNews = "EconomicNews";
	public static final String FinancialProduct = "FinancialProduct";
	public static final String Gold = "Gold";
	public static final String GoldTrendChart = "GoldTrendChart";
	public static final String Indexinfo = "Indexinfo";
	public static final String InterNews = "InterNews";
	public static final String MarketActivity = "MarketActivity";
	public static final String MetalInformation = "MetalInformation";
	public static final String Policy = "Policy";
	public static final String ProFund = "ProFund";
	public static final String ProBond = "ProBond";
	public static final String Snote = "Snote";
	public static final String Insurance = "Insurance";
	public static final String Shibor = "Shibor";
	public static final String Silver = "Silver";
	public static final String SilverTrendChart = "SilverTrendChart";
	public static final String Stockmarket = "Stockmarket";
	public static final String Authority = "Authority";
	public static final String Role = "Role";
	public static final String User = "User";	
	
	public static final String CreditActivity = "CreditActivity";
	public static final String CreditCard = "CreditCard";
	public static final String CreditCardType = "CreditCardType";
	public static final String CreditFee = "CreditFee";
	public static final String CreditProcess = "CreditProcess";
	public static final String RecomActivity = "RecomActivity";
	public static final String RecomProduct = "RecomProduct";
	public static final String Shop = "Shop";
	public static final String ShopType = "ShopType";
	public static final String SpecialBusiness = "SpecialBusiness";
	
	public static final String ActionsDef = "ActionsDef";
	
	private static  Map<String, String> map;
	
	static{
		map = new HashMap<String, String>();
		map.put(Dict, "数据字典");
		map.put(DictItem, "字典明细");
		map.put(CrawlRecord, "网页抓取日志");
		map.put(LoginLog, "登录日志");
		
		map.put(BjInformation, "本行资讯");
		map.put(CreditRates, "贷款利率");
		map.put(DepositRates, "存款利率");
		map.put(EconomicNews, "经济观察");
		map.put(FinancialProduct, "和讯理财产品");
		map.put(Gold, "黄金资讯");
		map.put(GoldTrendChart, "黄金趋势图");
		map.put(Indexinfo,"指数资讯");
		map.put(InterNews,"国际新闻");
		map.put(MarketActivity,"优惠活动" );
		map.put(MetalInformation, "贵金属资讯");
		map.put(Policy, "政策法规");
		map.put(ProFund, "基金信息");
		map.put(ProBond, "债券信息");
		map.put(Snote, "理财产品");
		map.put(Insurance, "保险信息");
		map.put(Shibor, "shibor利率");
		map.put(Silver, "白银资讯");
		map.put(SilverTrendChart, "白银趋势图");
		map.put(Stockmarket, "股票行情");
		map.put(Authority, "资源权限");
		map.put(Role, "角色信息");
		map.put(User, "用户信息");
		
		map.put(CreditActivity, "精彩活动");
		map.put(CreditCard, "信用卡");
		map.put(CreditCardType, "信用卡类型");
		map.put(CreditFee, "收费标准");
		map.put(CreditProcess, "申请流程");
		map.put(RecomActivity, "主推活动");
		map.put(RecomProduct, "主打产品");
		map.put(Shop, "惠享商户");
		map.put(ShopType, "商户类型");
		map.put(SpecialBusiness, "特色业务");
		map.put(ActionsDef, "action定义");
		
	}
	
	public static final String getModuleLabel(String moduleName){
		return map.get(moduleName);
	}
	
	
}
