

const ReportDownload = () => {
    const handleDownload = () => {
        fetch("http://localhost:8080/report/download/pdf")
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Network response was not ok: ${response.status} ${response.statusText}`);
                }
                return response.blob(); // Convert to Blob for PDF
            })
            .then((blob) => {
                const url = window.URL.createObjectURL(blob);
                const newWindow = window.open(url);

                // Add a flag to prevent multiple print calls
                let hasPrinted = false;

                if (newWindow) {
                    newWindow.onload = () => {
                    if (!hasPrinted) {
                        hasPrinted = true; // Set flag to true
                        newWindow.print(); // Trigger print
                        newWindow.onafterprint = () => newWindow.close(); // Optionally close the window after printing
                    }
                }
            };
            })
            .catch((error) => {
                console.error("There was an error downloading the report!", error);
            });


    };

    return (
        <div>
            <button onClick={handleDownload}>Print Report</button>
        </div>
    );
};

export default ReportDownload;
