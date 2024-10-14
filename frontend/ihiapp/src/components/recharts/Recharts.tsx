
import { LineCharts } from './charts/LineCharts';
import { BarCharts } from './charts/BarCharts';
import { PieCharts } from './charts/PieCharts';
import { AreaCharts } from './charts/AreaCharts';
import RadarCharts from './charts/RadarCharts';
import { ScatterCharts } from './charts/ScatterCharts';
import ReportDownload from '../printpdf/ReportDownload';
import { useAuth } from '../../context/AuthContext';
import ExportPdf from '../printpdf/ExportPdf';
import ExportExcel from '../printpdf/ExportExcel';


export const Recharts = () => {
    const {logout} = useAuth();
    return (
        <div className="w-full row">
            <div className='flex justify-end w-full pt-2 pr-2'>
                <button onClick={logout} className='px-2 py-1 border-2 border-red-500 border-solid'>Logout</button>
            </div>
            <div className='w-full flex items-center justify-center mb-5'>
                <h2>Charts with <span className='italic font-bold underline'>recharts</span>  library</h2>
            </div>
            <div className="relative flex items-center justify-center w-[93.4%] p-4 font-bold col-md-12 mx-auto">
                <div className='absolute px-2 py-1 border-2 border-blue-500 border-solid left-0 flex gap-x-5 mb-5'>
            <ExportExcel/>
            <ExportPdf/>
            <ReportDownload/>
                </div>
            </div>
            <div className='flex flex-wrap w-full justify-evenly gap-y-5'>
                <LineCharts/>
                <BarCharts/>
                <PieCharts/>
                <AreaCharts/>
                <RadarCharts/>
                <ScatterCharts/>
            </div>
           <footer className='p-5 text-center'>sample footer</footer>
        </div>
    )
}

