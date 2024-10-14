

const ExportExcel = () => {
    const handleDownload = () => {
        fetch("http://localhost:8080/report/xlsx")
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Network response was not ok: ${response.status} ${response.statusText}`);
                }
                return response.blob(); // Convert to Blob for PDF
            })

            .catch((error) => {
                console.error("There was an error downloading the report!", error);
            });


    };

    return (
        <div>
            <button onClick={handleDownload}>Export Excel</button>
        </div>
    );
};

export default ExportExcel;
