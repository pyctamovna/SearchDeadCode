use super::Detector;
use crate::analysis::DeadCode;
use crate::graph::Graph;

pub struct UnusedClassDetector;

impl UnusedClassDetector {
    pub fn new() -> Self {
        Self
    }
}

impl Detector for UnusedClassDetector {
    fn detect(&self, _graph: &Graph) -> Vec<DeadCode> {
        // Detection is handled by ReachabilityAnalyzer
        Vec::new()
    }
}

impl Default for UnusedClassDetector {
    fn default() -> Self {
        Self::new()
    }
}
