import { Bar, BarChart, CartesianGrid, Legend, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

import { ChartData } from "../../../types/Types";
import { RootState } from "../../../redux/store/store";
import { fetchBilledManHoursRequest } from "../../../redux/billedManHour/billedState";

export const BarCharts = () => {
    const dispatch = useDispatch();

    // Accessing state from Redux store
    const { billedMHInfo, isLoading, error } = useSelector(
        (state: RootState) => state.billedManHoursReducer
    );

    // Dispatch the fetch action when the component mounts
    useEffect(() => {
        dispatch(fetchBilledManHoursRequest());
    }, [ dispatch ]);

  

    if (isLoading) {
        return <div className="border-2 border-blue-500 border-solid w-[45%] h-[450px] flex justify-center items-center">Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    // Transform the billedManhourInfo into the desired format
    const chartData = billedMHInfo.map((item: ChartData) => ({
        label: item.bmdate, 
        TBMH: item.tbmh,   
        MOV: item.mov,     
        TBMHUTD: item.tbmhutd, 
        TSMHUTD: item.tsmhutd,
        SMH: item.smh  
    }));

    return (
        <>
            <div className='w-[45%] border-2 border-blue-500 border-solid p-5'>
                <div className="section col-md-6">
                    <h3 className="text-center bg-blue-500 section-title">Bar Chart</h3>
                    <div className="section-content">
                        <ResponsiveContainer width="100%" height={300}>
                            <BarChart data={chartData} margin={{ top: 15, right: 0, bottom: 15, left: 0 }}>
                                <XAxis dataKey="label" />
                                <YAxis />
                                <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
                                <Tooltip formatter={(value, name) => [ `${name}: ${value} hrs`, '' ]} />
                                <Legend />
                                <Bar dataKey="TBMH" fill="#FB8833" />
                                <Bar dataKey="MOV" fill="#17A8F5" />
                                <Bar dataKey="TBMHUTD" fill="#FBBF24" />
                                <Bar dataKey="TSMHUTD" fill="#F87171" />
                                <Bar dataKey="SMH" fill="#A78BFA" />
                            </BarChart>
                        </ResponsiveContainer>
                    </div>
                </div>
            </div>
        </>
    );
};
