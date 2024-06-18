
USE LMSDB;
DROP PROCEDURE if exists [ProcessReturn]
GO  

CREATE PROCEDURE [ProcessReturn] 
    
	@BarcodeId varchar(10)

AS   
BEGIN

Declare @Err_Msg AS varchar(50)

IF (Select 1 from BookCopies where BarcodeID=@BarcodeId)=1
	BEGIN
	--perform update
		BEGIN TRY
			BEGIN TRAN

			Update BookCopies
			SET Status=1 where BarcodeID=@BarcodeId

			COMMIT TRAN
		END TRY
		BEGIN CATCH
			SET @Err_Msg=ERROR_MESSAGE()
			ROLLBACK TRAN
		END CATCH
	END	
ELSE
SET @Err_Msg='Barcode not exist'

	


IF (@Err_Msg is null)	
	SELECT 'SUCCESS' AS tranState
ELSE
	SELECT @Err_Msg AS tranState

END
GO  
--execute [ProcessReturn] '8448976455'



