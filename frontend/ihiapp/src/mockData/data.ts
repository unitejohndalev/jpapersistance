import { AreaData, ChartData, PieData, RadarData, ScatterData } from "../types/Types";

export const chartData: ChartData[] = [
    { label: 'Jan', TBMH: 21.12, MOV:23,TBMHUTD:65,TSMHUTD:34,SMH:57 },
    { label: 'Feb', TBMH: 35.01, MOV:23,TBMHUTD:45,TSMHUTD:34,SMH:65 },
    { label: 'Mar', TBMH: 75, MOV:23,TBMHUTD:55,TSMHUTD:34,SMH:75 },
    { label: 'Apr', TBMH: 51, MOV:53,TBMHUTD:45,TSMHUTD:34,SMH:25 },
    { label: 'May', TBMH: 41, MOV:73,TBMHUTD:42.10,TSMHUTD:34,SMH:15 },
    { label: 'Jun', TBMH: 47, MOV:34,TBMHUTD:45.34,TSMHUTD:34,SMH:95 },
    { label: 'Jul', TBMH: 21, MOV:23,TBMHUTD:49,TSMHUTD:34,SMH:5 },
    { label: 'Aug', TBMH: 35, MOV:33,TBMHUTD:55,TSMHUTD:34,SMH:45 },
    { label: 'Sept',TBMH: 75,MOV:67,TBMHUTD:35,TSMHUTD:34,SMH:55.04 },
    { label: 'Oct', TBMH: 51, MOV:23.11,TBMHUTD:25,TSMHUTD:34,SMH:75 },
    { label: 'Nov', TBMH: 41, MOV:77,TBMHUTD:45,TSMHUTD:34,SMH:55 },
    { label: 'Dec', TBMH: 47, MOV:23,TBMHUTD:45,TSMHUTD:34,SMH:55 }
];


export const pieData: PieData[] = [
      {
         name: "Apple",
         value: 54.85
      },
      {
         name: "Samsung",
         value: 47.91
      },
      {
         name: "Redmi",
         value: 16.85
      },
      {
         name: "One Plus",
         value: 16.14
      },
      {
         name: "Others",
         value: 10.25
      }
   ];


export const areaData : AreaData[] = [
        { name: "A", x: 12, y: 23, z: 122 },
        { name: "B", x: 22, y: 3, z: 73 },
        { name: "C", x: 13, y: 15, z: 32 },
        { name: "D", x: 42, y: 35, z: 23 },
        { name: "E", x: 51, y: 45, z: 20 },
        { name: "F", x: 16, y: 25, z: 29 },
        { name: "G", x: 17, y: 17, z: 61 },
        { name: "H", x: 81, y: 32, z: 45 },
        { name: "I", x: 19, y: 43, z: 93 },
    ];


export const radarData : RadarData[] = [
{ subject: "Math", A: 120, B: 110, fullMark: 150 },
{ subject: "Science", A: 98, B: 130, fullMark: 150 },
{ subject: "English", A: 86, B: 130, fullMark: 150 },
{ subject: "History", A: 99, B: 100, fullMark: 150 },
{ subject: "Geography", A: 85, B: 90, fullMark: 150 },
{ subject: "Art", A: 65, B: 85, fullMark: 150 },
];

export const scatterData: ScatterData[] = [
{ x: 10, y: 30, z: 10 },
{ x: 30, y: 40, z: 20 },
{ x: 45, y: 80, z: 30 },
{ x: 50, y: 50, z: 40 },
{ x: 70, y: 70, z: 50 },
{ x: 80, y: 90, z: 60 },
];
