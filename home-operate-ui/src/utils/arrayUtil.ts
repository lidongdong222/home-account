export function max(arr:Array<number|string>){
    let max:any;
    arr.forEach(e => {
        if(!max || e>max) max=e;
    });
    return max;
}