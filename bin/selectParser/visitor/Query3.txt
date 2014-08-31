
 SELECT * FROM(
   select mgmt.INST_ID,mgmt.inst_nm,info.tlr_id,info.tlr_nm,
   nvl(report.getonemeritpayatbymonth(info.tlr_id,$C(name=STARTTRAN_MONTH,required=true),$C(name=CLOSURE_DISTANCE,required=true)),0) startpay,
   nvl(report.getonemeritpayatbymonth(info.tlr_id,$C(name=ENDTRAN_MONTH,required=true),$C(name=CLOSURE_DISTANCE,required=true)),0) endpay
   from t_mms_inst_mgmt_closure closure,t_mms_inst_mgmt mgmt,t_mms_tlr_info info,t_sum_achiv_mngr_indv indv 
   where closure.closure_pid=$C(name=INST_ID,required=false)
   and closure.closure_id = mgmt.INST_ID 
   and info.INST_ID = mgmt.INST_ID
   and indv.tlr_id = info.tlr_id
   and indv.tlr_id = $C(name=TLR_ID,required=false)
   and (closure.CLOSURE_DISTANCE = $C(name=CLOSURE_DISTANCE,required=false) or $C(name=CLOSURE_DISTANCE,required=false)=100)
   group by mgmt.INST_ID,mgmt.inst_nm,info.tlr_id,info.tlr_nm
   ) where 	endPay-startPay>$C(name=MIN_PAY,required=false)*1000 order by xxx, (endPay-startPay) desc	
 	