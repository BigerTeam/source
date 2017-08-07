<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.station {
	position: absolute;
	width: 356px;
	height: 228px;
	background: #fff;
	border: 1px solid #298cce;
	left: 72px;
	top: 0;
	z-index: 200;
	display: none;
}

.station-info .item1 {
	width: 65px;
	text-align: center;
	font-weight: bold;
}

.station-info .item2 {
	width: 165px;
	text-align: center;
}

.station-info .item3 {
	width: 65px;
}

.station-info span {
	display: inline-block;
	float: left;
}

.station-info {
	position: absolute;
	top: 228px;
	left: -1px;
	width: 356px;
	height: 22px;
	line-height: 22px;
	background: #fff;
	border: 1px solid #298cce;
	color: #0;
}

.station-hd {
	height: 28px;
	line-height: 28px;
	background: #63c7e7;
	color: #fff;
	overflow: hidden;
}

.station-hd .zx {
	width: 50px;
	text-align: center;
	margin-right: 5px;
}

.station-hd .zm {
	width: 65px;
}

.station-hd .dzsj {
	width: 88px;
}

.station-hd .cfsj {
	width: 75px;
}

.station-hd span {
	display: block;
	height: 28px;
	line-height: 28px;
	float: left;
	text-align: center;
}

.station-hd .close {
	display: block;
	width: 15px;
	height: 10px;
	background: url(https://kyfw.12306.cn/otn/resources/images/icon.png) 0
		-400px;
	float: right;
	margin: -20px 7px -5px 0;
	display: inline;
}

.station-bd table {
	border: 0;
	width: 100%;
	background: #fff;
}

.station-bd {
	width: 356px;
	height: 200px;
	overflow-y: auto;
	overflow-x: hidden;
	color: #333;
}

img, ul, ol, li, dl, dt, dd {
	margin: 0;
	padding: 0;
	border: 0;
}

div, span, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a,
	abbr, acronym, address, code, del, dfn, em, img, q, dl, dt, dd, ol, ul,
	li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead,
	tr, th, td {
	border: 0;
	list-style: none;
	margin: 0;
	padding: 0;
}
</style>

</head>
<body>

	<div class="station" id="train_div_"
		style="z-index: 20001; display: block;">
		<b></b>
		<div class="station-info" id="train_span_">
			<span class="item1" style="color: rgb(51, 51, 51);">K22次 </span><span
				class="item2" style="color: rgb(51, 51, 51);">桂林北<em>--&gt;</em>北京西
			</span><span class="item3" style="color: rgb(51, 51, 51);">快速</span><span
				class="item4" style="color: rgb(51, 51, 51);">有空调</span>
		</div>
		<div class="station-hd">
			<span class="zx">站序</span> <span class="zm">站名</span> <span
				class="dzsj">到站时间</span> <span class="cfsj">出发时间</span> <span
				class="tlsj">停留时间</span> <a class="close" title="关闭"
				href="javascript:" onclick="myStopStation.close()"></a>
		</div>
		<div class="station-bd">
			<table>
				<tbody id="train_table_">
					<tr>
						<td width="50" class="tc" style="color: #999;">01</td>
						<td width="75" style="color: #999;">桂林北</td>
						<td width="75" style="color: #999;">----</td>
						<td width="75" style="color: #999;">19:30</td>
						<td style="color: #999;">----</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">02</td>
						<td width="75" style="color: #999;">兴安北</td>
						<td width="75" style="color: #999;">20:08</td>
						<td width="75" style="color: #999;">20:12</td>
						<td style="color: #999;">4分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">03</td>
						<td width="75" style="color: #999;">全州南</td>
						<td width="75" style="color: #999;">20:45</td>
						<td width="75" style="color: #999;">21:02</td>
						<td style="color: #999;">17分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">04</td>
						<td width="75" style="color: #999;">东安东</td>
						<td width="75" style="color: #999;">21:46</td>
						<td width="75" style="color: #999;">21:50</td>
						<td style="color: #999;">4分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">05</td>
						<td width="75" style="color: #999;">永州</td>
						<td width="75" style="color: #999;">22:16</td>
						<td width="75" style="color: #999;">22:26</td>
						<td style="color: #999;">10分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">06</td>
						<td width="75" style="color: #999;">祁东</td>
						<td width="75" style="color: #999;">23:04</td>
						<td width="75" style="color: #999;">23:07</td>
						<td style="color: #999;">3分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">07</td>
						<td width="75" style="color: #999;">衡阳</td>
						<td width="75" style="color: #999;">00:03</td>
						<td width="75" style="color: #999;">00:09</td>
						<td style="color: #999;">6分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">08</td>
						<td width="75" style="color: #999;">长沙</td>
						<td width="75" style="color: #999;">02:16</td>
						<td width="75" style="color: #999;">02:29</td>
						<td style="color: #999;">13分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">09</td>
						<td width="75" style="color: #999;">岳阳</td>
						<td width="75" style="color: #999;">03:53</td>
						<td width="75" style="color: #999;">04:08</td>
						<td style="color: #999;">15分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">10</td>
						<td width="75" style="color: #999;">武昌</td>
						<td width="75" style="color: #999;">06:58</td>
						<td width="75" style="color: #999;">07:12</td>
						<td style="color: #999;">14分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">11</td>
						<td width="75" style="color: #999;">孝感</td>
						<td width="75" style="color: #999;">08:14</td>
						<td width="75" style="color: #999;">08:24</td>
						<td style="color: #999;">10分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">12</td>
						<td width="75" style="color: #999;">花园</td>
						<td width="75" style="color: #999;">08:45</td>
						<td width="75" style="color: #999;">08:48</td>
						<td style="color: #999;">3分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">13</td>
						<td width="75" style="color: #999;">信阳</td>
						<td width="75" style="color: #999;">10:04</td>
						<td width="75" style="color: #999;">10:07</td>
						<td style="color: #999;">3分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">14</td>
						<td width="75" style="color: #999;">驻马店</td>
						<td width="75" style="color: #999;">11:07</td>
						<td width="75" style="color: #999;">11:16</td>
						<td style="color: #999;">9分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">15</td>
						<td width="75" style="color: #999;">遂平</td>
						<td width="75" style="color: #999;">11:30</td>
						<td width="75" style="color: #999;">11:33</td>
						<td style="color: #999;">3分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">16</td>
						<td width="75" style="color: #999;">西平</td>
						<td width="75" style="color: #999;">11:50</td>
						<td width="75" style="color: #999;">11:53</td>
						<td style="color: #999;">3分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">17</td>
						<td width="75" style="color: #999;">漯河</td>
						<td width="75" style="color: #999;">12:12</td>
						<td width="75" style="color: #999;">12:15</td>
						<td style="color: #999;">3分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">18</td>
						<td width="75" style="color: #999;">许昌</td>
						<td width="75" style="color: #999;">13:06</td>
						<td width="75" style="color: #999;">13:09</td>
						<td style="color: #999;">3分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">19</td>
						<td width="75" style="color: #999;">郑州</td>
						<td width="75" style="color: #999;">14:05</td>
						<td width="75" style="color: #999;">14:20</td>
						<td style="color: #999;">15分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">20</td>
						<td width="75" style="color: #999;">新乡</td>
						<td width="75" style="color: #999;">15:18</td>
						<td width="75" style="color: #999;">15:22</td>
						<td style="color: #999;">4分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">21</td>
						<td width="75" style="color: #999;">鹤壁</td>
						<td width="75" style="color: #999;">16:01</td>
						<td width="75" style="color: #999;">16:03</td>
						<td style="color: #999;">2分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc" style="color: #999;">22</td>
						<td width="75" style="color: #999;">安阳</td>
						<td width="75" style="color: #999;">16:32</td>
						<td width="75" style="color: #999;">16:34</td>
						<td style="color: #999;">2分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc">23</td>
						<td width="75">邯郸</td>
						<td width="75">17:09</td>
						<td width="75">17:13</td>
						<td>4分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc">24</td>
						<td width="75">邢台</td>
						<td width="75">17:44</td>
						<td width="75">17:47</td>
						<td>3分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc">25</td>
						<td width="75">石家庄</td>
						<td width="75">19:16</td>
						<td width="75">19:20</td>
						<td>4分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc">26</td>
						<td width="75">定州</td>
						<td width="75">20:07</td>
						<td width="75">20:09</td>
						<td>2分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc">27</td>
						<td width="75">保定</td>
						<td width="75">20:46</td>
						<td width="75">20:50</td>
						<td>4分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc">28</td>
						<td width="75">徐水</td>
						<td width="75">21:07</td>
						<td width="75">21:22</td>
						<td>15分钟</td>
					</tr>
					<tr>
						<td width="50" class="tc">29</td>
						<td width="75">北京西</td>
						<td width="75">22:48</td>
						<td width="75">22:48</td>
						<td>----</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>



