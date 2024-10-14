
import { ScatterChart, CartesianGrid, XAxis, YAxis, Tooltip, Scatter, ResponsiveContainer } from "recharts"
import { scatterData } from "../../../mockData/data"
import { CustomTooltipProps } from "../../../types/Types";


export const ScatterCharts = () => {
    const CustomTooltip = ({ active, payload }: CustomTooltipProps) => {

        if (active && payload && payload.length > 0) {
            return (
                <div
                    className="custom-tooltip"
                    style={{
                        backgroundColor: "#ffff",
                        padding: "5px",
                        border: "1px solid #cccc"
                    }}
                >
                    <label>{`${payload[ 0 ].name} : ${payload[ 0 ].value}%`}</label>
                </div>
            );
        }
        return null;
    };
    return (
        <>
            <div className='w-[45%] border-2 border-blue-500 border-solid p-5'>
                <h3 className="section-title bg-blue-500 text-center">Scatter Chart</h3>
                <ResponsiveContainer width="100%" height={300}>
                    <ScatterChart>
                        <CartesianGrid />
                        <XAxis type="number" dataKey="x" name="X" />
                        <YAxis type="number" dataKey="y" name="Y" />
                        <Tooltip cursor={{ strokeDasharray: "3 3" }} />
                        <Scatter data={scatterData} fill="#8884d8" />
                        <Tooltip content={<CustomTooltip />} />
                    </ScatterChart>
                </ResponsiveContainer>

            </div>
        </>
    )
}
