<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctxstatic}/train/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctxstatic}/train/css/bootstrap-theme.min.css" />
<script type="text/javascript"
	src="${ctxstatic}/train/js/train_utils.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/train_list.js"></script>


<c:if test="${not empty list}">
	<div>
		共计<span class="trainSize">${fn:length(list)}</span>个车次 <span
			class="fr"> <input type="checkbox" class="check"
			id="avail_ticket"> <label for="avail_ticket"
			style="cursor: pointer;">显示全部可预订车次 </label>
		</span>
	</div>
</c:if>
<table style="margin: 0 auto;" class="table table-bordered" id="train">
	<thead align="center">
		<td><input type="checkbox">车次</td>
		<td>出发站<br />到达站
		</td>
		<td>出发时间<br />到达时间
		</td>
		<td id="stratTimeSort">历时</td>
		<td>商务座</td>
		<td>特等座</td>
		<td>一等座</td>
		<td>二等座</td>
		<td>高级软卧</td>
		<td>软卧</td>
		<td>硬卧</td>
		<td>软座</td>
		<td>硬座</td>
		<td>无座</td>
		<td>其他</td>
		<td>备注</td>
	</thead>
	<tbody id="tbody">
		<c:if test="${not empty list}">
			<c:forEach items="${list}" var="train">
				<tr align="center">
					<td><input type="checkbox" class="trainCode"
						value="${train.stationTrainCode }">${train.stationTrainCode }</td>
					<td><c:if test="${not empty train.startFlag}">始</c:if>
						${train.fromStationName }<br /> <c:if
							test="${not empty train.endFlag}">终</c:if> ${train.toStationName }
					</td>
					<td>${train.startTime }<br />${train.arriveTime}
					</td>
					<td id="lishi" lishi="${train.lishi}">${train.lishi}</td>
					<td>${train.swzNum }</td>
					<td>${train.tzNum }</td>
					<td>${train.zyNum }</td>
					<td>${train.zeNum }</td>
					<td>${train.grNum }</td>
					<td>${train.rwNum }</td>
					<td>${train.ywNum }</td>
					<td>${train.rzNum }</td>
					<td>${train.yzNum }</td>
					<td>${train.wzNum }</td>
					<td>${train.qtNum }</td>
					<td id="secretStr" secretStr="${train.secretStr}"><c:if
							test="${empty train.secretStr }">
							${train.buttonTextInfo}
						</c:if> <c:if test="${not empty train.secretStr}">
							<input type="button" class="btn btn-default" role="button"
								onclick="submitOrderRequest('${train.secretStr}','${train.startTime }','${train.trainNo}','${train.fromStationTelecode}','${train.toStationTelecode}','${train.ypInfo}','${train.fromStationName}','${train.toStationName}','${train.location_code}','${train.stationTrainCode}')"
								value="${train.buttonTextInfo}">
						</c:if></td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>