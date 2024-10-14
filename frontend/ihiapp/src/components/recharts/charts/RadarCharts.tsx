import { RadarChart, PolarGrid, PolarAngleAxis, PolarRadiusAxis, Radar, Legend, Tooltip, ResponsiveContainer } from "recharts"
import { radarData } from "../../../mockData/data"
import { CustomTooltipProps } from "../../../types/Types";


const RadarCharts = () => {
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
            <div className='w-[45%] border-2 border-orange-500 border-solid p-5'>
                <h3 className="section-title bg-orange-500 text-center">Radar Chart</h3>
                <ResponsiveContainer width="100%" height={300}>
                    <RadarChart data={radarData}>
                        <PolarGrid />
                        <PolarAngleAxis dataKey="subject" />
                        <PolarRadiusAxis angle={30} domain={[ 0, 150 ]} />
                        <Radar name="Student A" dataKey="A" stroke="#8884d8" fill="#8884d8" fillOpacity={0.6} />
                        <Radar name="Student B" dataKey="B" stroke="#82ca9d" fill="#82ca9d" fillOpacity={0.6} />
                        <Legend />
                        <Tooltip content={<CustomTooltip />} />
                    </RadarChart>
                </ResponsiveContainer>

            </div>
        </>
    )
}

export default RadarCharts