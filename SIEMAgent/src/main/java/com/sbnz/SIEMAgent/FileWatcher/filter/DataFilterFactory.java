package com.sbnz.SIEMAgent.FileWatcher.filter;

import com.sbnz.SIEMAgent.Log.LogEntry;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;


@Component
public class DataFilterFactory  {

    private DataFilterFactory(){};

    public DataFilter generateDataFilter(DataFilterSeed seed)
    {
        DataFilter filter=null;
        switch (seed.signature){
            case LOGLVL:
                filter = createLogLVLFilter(seed.predicate);
                break;
            case MESSAGE:
                filter = createMessageFilter(seed.predicate);
                break;
            case CATEGORY:
                filter = createCategoryFilter(seed.predicate);
                break;
            case NEGATION:
                filter = createNegationFilter(seed.predicate);
                break;
            case COMPOSITE:
                filter = createCompositeFilter(seed.predicate);
                break;
            case INFOSYSTEM:
                filter = crateInfoSystemFilter(seed.predicate);
                break;
            default:
            case NOFILTER:
                filter = createNoFilter();
                break;
        }
        return filter;
    }

    private DataFilter crateInfoSystemFilter(Object predicate) {
        //TODO: Maybe change this after we find out what is info system actually :)
        return new DataFilter(FilterSignature.INFOSYSTEM) {
            int match =  tryCast(predicate);
            @Override
            public boolean applyFilter(LogEntry entry) {
                return  entry.getLogLevel()==match;
            }
        };
    }

    private <T> T tryCast(Object object){
        T rets = null;
        try {
            rets = (T) object;
        }catch (ClassCastException e){
            throw new IllegalArgumentException();
        }
        return rets;
    }

    private DataFilter createLogLVLFilter(Object predicate) {
        return new DataFilter(FilterSignature.LOGLVL) {
            int match =  tryCast(predicate);
            @Override
            public boolean applyFilter(LogEntry entry) {
                return  entry.getLogLevel()==match;
            }
        };
    }

    private DataFilter createCategoryFilter(Object predicate) {
        return new DataFilter(FilterSignature.CATEGORY) {
            String match =  tryCast(predicate);
            @Override
            public boolean applyFilter(LogEntry entry) {
                return entry.getCategory() != null && entry.getMessage().contains(match);
            }
        };
    }

    private DataFilter createMessageFilter(Object predicate) {
        return new DataFilter(FilterSignature.MESSAGE) {
            String match =  tryCast(predicate);
            @Override
            public boolean applyFilter(LogEntry entry) {
                return entry.getMessage()!=null && entry.getMessage().contains(match);
            }
        };
    }

    private DataFilter createNegationFilter(Object predicate) {
        DataFilterSeed negationSeed = tryCast(predicate);

        return new DataFilter(FilterSignature.NEGATION) {
            DataFilter filter = generateDataFilter(negationSeed);

            @Override
            public boolean applyFilter(LogEntry entry) {
                return !filter.applyFilter(entry);
            }
        };

    }

    private DataFilter createNoFilter() {
        return new DataFilter(FilterSignature.NOFILTER) {
            @Override
            public boolean applyFilter(LogEntry entry) {
                return true;
            }
        };
    }

    private DataFilter createCompositeFilter(Object predicate) {
        List<DataFilterSeed> filterSeeds = tryCast(predicate);
        List<DataFilter> compositeFilters = new ArrayList<>();
        for(DataFilterSeed compositeSeed : filterSeeds){
            compositeFilters.add(generateDataFilter(compositeSeed));
        }
        return new DataFilter(FilterSignature.COMPOSITE) {
            List<DataFilter> filters = compositeFilters;
            @Override
            public boolean applyFilter(LogEntry entry) {
                for(DataFilter filter1 : filters){
                    if(!filter1.applyFilter(entry)){
                        return false;
                    }
                }
                return true;
            }
        };
    }
}
