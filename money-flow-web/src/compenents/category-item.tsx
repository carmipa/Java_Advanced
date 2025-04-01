import {Book, Icon} from "lucide-react";
import CrudDropdown from "@/compenents/crud-dropdown";

interface CategoryItemProps{
    category: Category
}

export default function CategoriesItem() {
    return(
        <div className="flex justify-between mt-2">
            <div className="flex gap-2">
                <Icon name={category.icons}>
                <span>{category.name}</span>
            </div>

            <div className="flex gap-2">
                <CrudDropdown />
            </div>
        </div>
    )

}