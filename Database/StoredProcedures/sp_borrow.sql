
USE LMSDB;
DROP PROCEDURE if exists [Borrow]
GO  

CREATE PROCEDURE [Borrow] 
    @Ac varchar(10),   
	@BarcodeId varchar(10)


AS   
BEGIN
Declare @Err_Msg AS varchar(100)

Declare @BorrowRecordID AS char(4)
Declare @BorrowDate AS date
Declare @DueDate As date
Declare @RenewalTimes As smallint
Declare @State As smallint
Declare @ReaderId As char(4)

SET @BorrowRecordID=(select FORMAT(MAX(BorrowRecordID)+1,'0000') from BorrowHistory)
SET @BorrowDate=(SELECT FORMAT(GETDATE(),'yyyy-MM-dd'))
SET @DueDate=(SELECT FORMAT(GETDATE()+14,'yyyy-MM-dd'))
SET @RenewalTimes=0
SET @State=1
SET @ReaderId=(SELECT ReaderId FROM Reader r WHERE r.Ac=@Ac )


--checkbarcodeid avalibility
IF ( SELECT status from BookCopies where BarcodeID=@BarcodeId )=1
	BEGIN
		--checkreader quota
		IF (SELECT COUNT(*) FROM BorrowHistory where ReaderId=@ReaderId AND State=1) < (Select Top 1 MaxQuota from Reader where Ac=@Ac)
			BEGIN
				--insert borrow history & update bookcopies status
				
				BEGIN TRY
					BEGIN TRAN
					Update BookCopies
					SET Status=2 where BarcodeID=@BarcodeId

					INSERT INTO BorrowHistory(BorrowRecordID,BorrowDate,DueDate,RenewalTimes,State,BarCodeID,ReaderId)
					Values (@BorrowRecordID,@BorrowDate,@DueDate,@RenewalTimes,@State,@BarcodeId,@ReaderId)
					COMMIT TRAN
				END TRY
				BEGIN CATCH
					SET @Err_Msg=ERROR_MESSAGE()
					ROLLBACK TRAN
				END CATCH
				
			END
	ELSE 
		SET @Err_Msg='The Reader account '+@Ac+ 'has reached his borrowing quota limit'
END
ELSE 
	SET @Err_Msg='The Book with ID '+@BarcodeId+ 'is not avaliable'

IF (@Err_Msg is null)	
	SELECT 'SUCCESS' AS tranState,@BorrowRecordID AS borrowId,@DueDate As dueDate

ELSE
	SELECT @Err_Msg AS tranState


END
GO  
--execute [Borrow] 'ivan','8661995532'
