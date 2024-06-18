
USE LMSDB;
DROP PROCEDURE if exists [Renew]
GO  

CREATE PROCEDURE [Renew] 
	@barcodeId varchar(10)

AS   
BEGIN
Declare @Err_Msg AS varchar(50)

Declare @NewDueDate As date
Declare @Title As varchar(50)
SET @NewDueDate= DATEADD(day,14,(select DueDate from BorrowHistory where state=1 and BarCodeID=@barcodeID))
SET @Title=(Select b.title from book b inner join BookCopies bc on  b.ISBN=bc.ISBN where bc.BarcodeID=@barcodeId)
BEGIN TRY
	BEGIN TRAN
	update BorrowHistory
	set DueDate=@NewDueDate,
	 RenewalTimes=(select RenewalTimes from BorrowHistory where state=1 and BarCodeID=@barcodeID)+1
	where state=1 and BarCodeID=@barcodeID
	COMMIT TRAN
END TRY
BEGIN CATCH
	SET @Err_Msg=ERROR_MESSAGE()
	ROLLBACK TRAN
END CATCH
				

IF (@Err_Msg is null)	
	SELECT 'SUCCESS' AS tranState ,@NewDueDate As newDueDate,@Title As title,@barcodeId As barcodeID
ELSE
	SELECT @Err_Msg AS tranState,@Title As title,@barcodeId As barcodeID


END
GO  
--execute [Renew] '5733779676'



