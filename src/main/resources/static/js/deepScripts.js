$(document).ready(function(){
	$('#frameNumber,#frameThicknessSize,#frameSizeWidth,#frameSizeHeight,.quantity,.sellingPrice,#mobileNumber,#price,#cRate,#depositeBillAmount,#expenseAmount')
			.keypress(function(e)
               {
                 var key_codes = [46,48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 0, 8];

                 if (!($.inArray(e.which, key_codes) >= 0)) {
                   e.preventDefault();
               }
    });
});
function validateDate(_obj,e){
		//MM/DD/YYYY
		if(/^(([012]\d)|3[01])\/((0\d)|(1[012]))\/\d{4}$/.test($(_obj).val())){
			$('#errdate').text('');
			return true;
		}else{
			$('#billingDate').val();
			$('#errdate').text('Please Enter Valid Date (DD/MM/YYYY)');
			return false;
		}
}
function removeRecord(){
		var isOK = confirm("Are you sure to Delete ?");
		if(isOK){
			return true;
		}
	return false;
}
function createFinalBill(_obj){
	var flag = true;
	var sellingPriceId;
	var clientTypeId;
	var id = $(_obj).attr("id");
	
	$('.sellingPrice,.frameDetails').each(function(i) {
		var rowCountId = i+1;
		sellingPriceId = -1;
		clientTypeId = -1;
		sellingPriceId=$('#sellingPrice_'+rowCountId).val();
		clientTypeId =$('#clientType_'+rowCountId).find('option:selected').val();
		var frameDetailsId= $('#frameDetails_'+rowCountId).val();
		//alert(sellingPriceId+'='+clientTypeId);
		if(sellingPriceId < 0  && frameDetailsId < 0){
					flag= false;
				}
		});
	if(flag){
		var isOK = confirm("Are you sure ?");
		if(isOK){
			if(id == 'endorseBill'){
				$('#frmPvqBillDesk').attr('action','/deepStudio/pvqManager/pvqBillDesk/create?billStatus=Endorsed_Bill');
		        $('#frmPvqBillDesk').submit();
			}else if(id == 'closeBill'){
				$('#frmPvqBillDesk').attr('action','/deepStudio/pvqManager/pvqBillDesk/create?billStatus=Closed_Bill');
		        $('#frmPvqBillDesk').submit();
			}
			return true;
		}else{
			return false;
		}
	}else{
		alert('Selling Price or Client Type undefined.');
		return false;
	}
	return false;	
}

function openQuatation(_obj,pvqBillId){
	//alert(pvqBillId);
	if(pvqBillId != null){
		var url = "http://localhost:8080/deepStudio/quatation/previewQuataion?custBillingId="+pvqBillId;
		window.open(url, '_blank');
		return true;
	}else{
		return false;
	}
}