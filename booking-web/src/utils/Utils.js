import { format } from "date-fns";

export function dateToStr( date){
    return format(date, "dd-MMM-yyyy h:mm a");
}